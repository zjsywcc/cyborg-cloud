package com.ese.cloud.client.controller.app;

import com.ese.cloud.client.api.UploadRequest;
import com.ese.cloud.client.entity.*;
import com.ese.cloud.client.service.*;
import com.ese.cloud.client.util.ByteUtil;
import com.ese.cloud.client.util.ReturnData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



/**
 * Created by wangchengcheng on 2017/11/9.
 */
@Controller
@RequestMapping("bluetooth")
public class BluetoothController {

    Logger logger = LoggerFactory.getLogger(BluetoothController.class);

    @Autowired
    BiosignalInfoService biosignalInfoService;

    @Autowired
    MonitorEMGInfoService monitorEMGInfoService;

    @Autowired
    MonitorEEGInfoService monitorEEGInfoService;

    @Autowired
    MonitorRRInfoService monitorRRInfoService;

    @Autowired
    MonitorTempInfoService monitorTempInfoService;


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestBody UploadRequest uploadRequest) {
        try {
            for (UploadRequest.ValuePair valuePair : uploadRequest.getValuePairs()) {
                String hexBiosignal = valuePair.getPacket();
                long timestamp = valuePair.getTimestamp();
                BiosignalInfo biosignalInfo = new BiosignalInfo();
                biosignalInfo.setTimestamp(timestamp);
                biosignalInfo.setPacket(hexBiosignal);
                biosignalInfo.setRead(false);
                biosignalInfoService.add(biosignalInfo);

                MonitorEMGInfo emgInfo = new MonitorEMGInfo();
                MonitorEEGInfo eegInfo = new MonitorEEGInfo();
                MonitorRRInfo rrInfo = new MonitorRRInfo();
                MonitorTempInfo tempInfo = new MonitorTempInfo();
                parseBiosignals(hexBiosignal, timestamp, emgInfo, rrInfo, eegInfo, tempInfo);
                logger.info("打印emgInfo: {}", emgInfo.toString());
                monitorEMGInfoService.add(emgInfo);
                monitorEEGInfoService.add(eegInfo);
                monitorRRInfoService.add(rrInfo);
                monitorTempInfoService.add(tempInfo);
            }
            return ReturnData.result(200, "上传成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("upload failed: {}", e.getMessage());
            return ReturnData.result(-1, String.format("上传失败 错误信息：%s", e.getMessage()), null);
        }
    }

    private void parseBiosignals(String hexStr, long timestamp, MonitorEMGInfo emgInfo, MonitorRRInfo rrInfo,
                                 MonitorEEGInfo eegInfo, MonitorTempInfo tempInfo) throws BiosignalParseException{
        try {
            byte[] biosignalBytes = ByteUtil.hexStrToByteArray(hexStr);
            double emgValue1 = biosignalBytes[37] & 0xFF;
            double emgValue2 = biosignalBytes[38] & 0xFF;
            double emgValue3 = biosignalBytes[39] & 0xFF;
            double emgValue4 = biosignalBytes[40] & 0xFF;
            double emgValue = (emgValue1 + emgValue2 + emgValue3 + emgValue4) / 4;
            double rrValue1 = biosignalBytes[33] & 0xFF;
            double rrValue2 = biosignalBytes[34] & 0xFF;
            double rrValue3 = biosignalBytes[35] & 0xFF;
            double rrValue4 = biosignalBytes[36] & 0xFF;
            double rrValue = (rrValue1 + rrValue2 + rrValue3 + rrValue4) / 4;
            double eegDeltaValue = getRawEEGVoltage(biosignalBytes[5], biosignalBytes[6], biosignalBytes[7]);
            double eegThetaValue = getRawEEGVoltage(biosignalBytes[8], biosignalBytes[9], biosignalBytes[10]);
            double eegLowalphaValue = getRawEEGVoltage(biosignalBytes[11], biosignalBytes[12], biosignalBytes[13]);
            double eegHighalphaValue = getRawEEGVoltage(biosignalBytes[14], biosignalBytes[15], biosignalBytes[16]);
            double eegLowbetaValue = getRawEEGVoltage(biosignalBytes[17], biosignalBytes[18], biosignalBytes[19]);
            double eegHighbetaValue = getRawEEGVoltage(biosignalBytes[20], biosignalBytes[21], biosignalBytes[22]);
            double eegLowgammaValue = getRawEEGVoltage(biosignalBytes[23], biosignalBytes[24], biosignalBytes[25]);
            double eegMidgammaValue = getRawEEGVoltage(biosignalBytes[26], biosignalBytes[27], biosignalBytes[28]);
            double eegAttentionValue = biosignalBytes[30] & 0xFF;
            double eegMediationValue = biosignalBytes[32] & 0xFF;
            double tempValue = getRawBodyTemp(biosignalBytes[1], biosignalBytes[2]);
            
            emgInfo.setEmgValue(emgValue);
            emgInfo.setRead(false);
            emgInfo.setTimestamp(timestamp);
            rrInfo.setRrValue(rrValue);
            rrInfo.setRead(false);
            rrInfo.setTimestamp(timestamp);
            eegInfo.setEegDelta(eegDeltaValue);
            eegInfo.setEegTheta(eegThetaValue);
            eegInfo.setEegLowalpha(eegLowalphaValue);
            eegInfo.setEegHighalpha(eegHighalphaValue);
            eegInfo.setEegLowbeta(eegLowbetaValue);
            eegInfo.setEegHighbeta(eegHighbetaValue);
            eegInfo.setEegLowgamma(eegLowgammaValue);
            eegInfo.setEegMidgamma(eegMidgammaValue);
            eegInfo.setEegAttention(eegAttentionValue);
            eegInfo.setEegMediation(eegMediationValue);
            eegInfo.setRead(false);
            eegInfo.setTimestamp(timestamp);
            tempInfo.setTempValue(tempValue);
            tempInfo.setRead(false);
            tempInfo.setTimestamp(timestamp);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BiosignalParseException("生理信号包格式不匹配");
        }

    }

    public class BiosignalParseException extends Exception {
        public BiosignalParseException(String message) {
            super(message);
        }
    }

    private static float getRawEEGVoltage(byte high, byte mid, byte low) {
        int h = (int) high;
        int m = ((int)mid) & 0xFF;
        int l = ((int)low) & 0xFF;
        int value = (h << 16) | (m << 8) | l;
        if(value > 32768)
            value -= 65536;
        float rawVoltage = (float)(value * (1.8 / 4096));
        return rawVoltage;
    }

    private static float getRawBodyTemp(byte highByte, byte lowByte) {
        int high = (int) highByte;
        int low = ((int) lowByte) & 0xFF;
        int rawTemp = ((high << 8) | low) / 100;
        return rawTemp;
    }


}