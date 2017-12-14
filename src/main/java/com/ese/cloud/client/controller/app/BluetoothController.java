package com.ese.cloud.client.controller.app;

import com.ese.cloud.client.api.UploadRequest;
import com.ese.cloud.client.entity.MonitorEMGInfo;
import com.ese.cloud.client.service.MonitorEMGInfoService;
import com.ese.cloud.client.util.ReturnData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wangchengcheng on 2017/11/9.
 */
@Controller
@RequestMapping("bluetooth")
public class BluetoothController {

    Logger logger = LoggerFactory.getLogger(BluetoothController.class);

    @Autowired
    MonitorEMGInfoService monitorEMGInfoService;


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestBody UploadRequest uploadRequest) {
        try {
            for (UploadRequest.ValuePair valuePair : uploadRequest.getValuePairs()) {
                MonitorEMGInfo monitorEMGInfo = new MonitorEMGInfo();
                monitorEMGInfo.setTimestamp(valuePair.getTimestamp());
                monitorEMGInfo.setValue(valuePair.getValue());
                monitorEMGInfo.setRead(false);
                monitorEMGInfoService.add(monitorEMGInfo);
            }
            return ReturnData.result(200, "上传成功", null);

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("upload failed: {}", e.getMessage());
            return ReturnData.result(-1, String.format("上传失败 错误信息：%s", e.getMessage()), null);
        }
    }


}