package com.ese.cloud.client.controller.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ese.cloud.client.entity.RecordInfo;
import com.ese.cloud.client.entity.ShortURL;
import com.ese.cloud.client.entity.Task;
import com.ese.cloud.client.service.RecordInfoService;
import com.ese.cloud.client.service.ShortURLService;
import com.ese.cloud.client.util.DateTimeUtil;
import com.ese.cloud.client.util.ReturnData;
import com.ese.cloud.client.util.ShortURLUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by mengchenyun on 2017/3/30.
 */
@Controller
@RequestMapping("s")
public class ShortURLController {

    static Logger logger = Logger.getLogger(ShortURLController.class);


    @Value("${short-url.expired-time}")
    long expireTime;

    @Autowired
    ShortURLService shortURLService;

    @Autowired
    RecordInfoService recordInfoService;

//    @Autowired
//    SendMsgUtil sendMsgUtil;

    private AtomicBoolean isSending = new AtomicBoolean(false);

    private final Task task = new Task();

    /**
     * 重定向至内容页面
     *
     * @param sURL
     * @param model
     * @return
     */
    @RequestMapping(value = "/{sURL}", method = RequestMethod.GET)
    public String redirectToRealURL(@PathVariable("sURL") String sURL, Model model) {
        ShortURL decode = shortURLService.findByShort(sURL);
        Date now = new Date();
        int diffMinute = 0;
        try {
            diffMinute = DateTimeUtil.diffMinutes(now, DateTimeUtil.parse(decode.getCreateTime()));
        } catch (Exception e) {
            logger.error("解析createTime出错", e);
        }
        if (decode == null) {
            return "redirect:/error/404";
        } else if (diffMinute >= expireTime) {
            return "redirect:/mobile/url_expired";
        } else {
            String phone = decode.getRealURL();
            model.addAttribute("phone", phone);
            return "s/phone";
        }
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "s/index";
    }

    /***
     * 列表
     * @param sSearch
     * @param sEcho
     * @param iDisplayStart
     * @param iDisplayLength
     * @param iColumns
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(
            @RequestParam("sSearch") String sSearch,//查询
            @RequestParam("sSearch_0") String phone,
            @RequestParam("sSearch_1") String status,
            @RequestParam("sSearch_2") String date,
            @RequestParam("sSearch_3") String driverType,
            @RequestParam("sEcho") String sEcho,//标识
            @RequestParam("iDisplayStart") int iDisplayStart,//开始位置
            @RequestParam("iDisplayLength") int iDisplayLength,//显示长度
            @RequestParam("iColumns") String iColumns) {

        //获取当前的subject
        Subject currentUser = SecurityUtils.getSubject();
        int level = (int) currentUser.getSession().getAttribute("role_level");
        String userId = (String) currentUser.getSession().getAttribute("user_id");

        List<ShortURL> list = shortURLService.pageFindFilterIndividual(iDisplayStart, iDisplayLength, phone, status, date, driverType);
        long count = shortURLService.countFilterIndividual(phone, status, date, driverType);

        JSONObject json = new JSONObject();
        json.put("sEcho", sEcho);
        json.put("iTotalRecords", count);//总条数
        json.put("iTotalDisplayRecords", count);//显示总条数
        json.put("aaData", JSONArray.toJSON(list));

        return json.toString();
    }

    /**
     * 添加短址
     *
     * @param sURL
     * @param realURL
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(@RequestParam String sURL,
                      @RequestParam String realURL,
                      @RequestParam String status) {

        ShortURL shortURL = new ShortURL();
        shortURL.setShortURL(sURL);
        shortURL.setRealURL(realURL);
        shortURL.setStatus(status);

        if (shortURLService.findByShort(sURL) == null) {
            if (shortURLService.add(shortURL)) {
                return ReturnData.result(0, "添加短址成功", null);
            } else {
                return ReturnData.result(-1, "添加短址失败", null);
            }
        } else {
            return ReturnData.result(-2, "短址已经存在", null);
        }
    }

    /**
     * 修改短址
     *
     * @param id
     * @param sURL
     * @param realURL
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(
            @RequestParam String id,
            @RequestParam String sURL,
            @RequestParam String realURL,
            @RequestParam String status,
            @RequestParam String createTime) {
        String temp = shortURLService.findById(id).getShortURL();
        ShortURL shortURL = new ShortURL();
        shortURL.setId(id);
        shortURL.setShortURL(sURL);
        shortURL.setRealURL(realURL);
        shortURL.setStatus(status);
        shortURL.setCreateTime(createTime);

        if (shortURLService.findByShort(sURL) != null && !temp.equals(sURL)) {
            return ReturnData.result(-2, "短址已经存在", null);
        } else {
            if (shortURLService.update(shortURL)) {
                return ReturnData.result(0, "修改短址成功", null);
            } else {
                return ReturnData.result(-1, "修改短址失败", null);
            }
        }

    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam String id) {

        if (shortURLService.delete(id)) {
            return ReturnData.result(0, "", null);
        } else {
            return ReturnData.result(-1, "", null);
        }

    }

    /**
     * 根据id查询用户信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById", method = RequestMethod.POST)
    @ResponseBody
    public String findById(@RequestParam String id) {
        ShortURL shortURL = shortURLService.findById(id);
        if (shortURL != null) {
            return ReturnData.result(0, "", JSON.toJSONString(shortURL));
        } else {
            return ReturnData.result(-1, "", null);
        }
    }

    @RequestMapping(value = "/encodeByPhone", method = RequestMethod.POST)
    @ResponseBody
    public String encodeByPhone(@RequestParam String phone) {
        try {
            String encode = ShortURLUtil.shortUrl(phone)[0];
            return ReturnData.result(0, "", JSON.toJSONString(encode));
        } catch (Exception e) {
            logger.error("encode phone to short url error: ", e);
            return ReturnData.result(-1, "", null);
        }
    }

    /**
     * 导入短地址
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam String driverType) {

        //获取当前的subject
        Subject currentUser = SecurityUtils.getSubject();
        String userid = (String) currentUser.getSession().getAttribute("user_id");

        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            if (bytes.length == 0) {
                return ReturnData.result(-2, "请先上传txt, 以换行分隔手机号", null);
            }
            String separator = System.getProperty("line.separator");
            String[] phoneList = new String(bytes).split(separator);
            for (String phone : phoneList) {
                phone = StringUtils.trimToNull(phone);
                if (phone == null) {
                    continue;
                }
                ShortURL shortURL = new ShortURL();
                shortURL.setRealURL(phone);
                String sURL = ShortURLUtil.shortUrl(phone)[0];
                shortURL.setShortURL(sURL);
                shortURL.setCreateTime(DateTimeUtil.convert(new Date()));
                shortURL.setStatus("未发送");
                shortURL.setUserId(userid);
                shortURL.setDriverType(driverType);

                if (shortURLService.findByShort(sURL) == null) {
                    shortURLService.add(shortURL);
                }
            }
            return ReturnData.result(0, "批量导入短址成功", null);
        } catch (IOException e) {
            logger.error("upload phone list error: ", e);
            return ReturnData.result(-1, "批量导入短址失败", null);
        }

    }

    /**
     * 批量发送短信
     *
     * @param sURL
     * @return
     */
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public String updateStatus(@RequestParam String sURL) {

        try {
            if (sURL == null || sURL.isEmpty()) {
                return ReturnData.result(-2, "请先选择要发送短信的手机", null);
            }
            String[] shortURLList = sURL.split(",");
            if (isSending.get()) {
                return ReturnData.result(-3, "有发送短信的任务正在进行中,请稍候再试", null);
            }
            synchronized (task) {
                new SendMsgThread(shortURLList).start();
            }
            return ReturnData.result(0, "正在发送...", null);
        } catch (Exception e) {
            isSending.set(false);
            task.setStatus("发送异常");
            logger.error("update send text msg status error: ", e);
            return ReturnData.result(-1, "发送短信失败", null);
        }
    }

    public class SendMsgThread extends Thread {

        private String[] shortURLList;

        public SendMsgThread(String[] shortURLList) {
            this.shortURLList = shortURLList;
        }

        public void run() {
            try {
                isSending.set(true);
                task.setTotal(shortURLList.length);
                task.setProgress(0);
                task.setFailure(0);
                task.setSuccess(0);
                task.setStatus("发送中...");
                for (String url : shortURLList) {
                    ShortURL shortURL = shortURLService.findByShort(url);
//                    String smsContent = sendMsgUtil.getMsgInfo(domain + "/mobile/" + shortURL.getShortURL());
                    if (shortURL != null && shortURL.getStatus().equals("未发送")) {
                        try {
//                            sendMsgUtil.sendMsg(shortURL.getRealURL(), smsContent);
                            shortURL.setStatus("已发送");
                            task.setSuccess(task.getSuccess() + 1);
                        } catch (Exception e) {
                            shortURL.setStatus("未发送");
                            logger.error("发送失败：", e);
                            task.setFailure(task.getFailure() + 1);
                        }
                        shortURLService.update(shortURL);
                    } else {
                        task.setFailure(task.getFailure() + 1);
                    }
                    task.setProgress(task.getProgress() + 1);
                    if (task.getProgress() == task.getTotal()) {
                        task.setStatus("发送完成");
                    }
                    sleep(800);
                }
                isSending.set(false);
            } catch (InterruptedException e) {
                isSending.set(false);
                task.setStatus("发送异常");
                e.printStackTrace();
            }
        }
    }

    /**
     * 批量删除
     *
     * @param sURL
     * @return
     */
    @RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
    @ResponseBody
    public String deleteAll(@RequestParam String sURL) {
        try {
            String[] shortURLList = sURL.split(",");
            for (String url : shortURLList) {
                shortURLService.deleteByShortURL(url);
            }
            return ReturnData.result(0, "", null);
        } catch (Exception e) {
            logger.error("delete all error: ", e);
            return ReturnData.result(-1, "", null);
        }
    }

    /**
     * 获取批量发送短信进度
     *
     * @return
     */
    @RequestMapping(value = "/progress", method = RequestMethod.POST)
    @ResponseBody
    public String getProgress() {
        return ReturnData.result(0, "", JSON.toJSONString(task));
    }

    /**
     * 单独发送短信
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    @ResponseBody
    public String sendMsg(@RequestParam String id) {
        ShortURL shortURL = shortURLService.findById(id);
        Date now = new Date();
        String nowStr = DateTimeUtil.convert(now);
        String phone = shortURL.getRealURL();
        RecordInfo recordInfo = recordInfoService.findByLoginMob(phone);
        if (recordInfo == null) {
            return ReturnData.result(-2, "未找到对应的记录", null);
        }
        String encode = ShortURLUtil.shortUrl(phone)[0];
        int canSend = 0;
        if (recordInfo.getSubmitStatus() == 1 && recordInfo.getStatus() == 0) {
            canSend = -1;
        }
        if (recordInfo.getSubmitStatus() == 1 && recordInfo.getStatus() == 1 && recordInfo.getResult() == 1) {
            canSend = -2;
        }


        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date shortDate = sdf.parse(shortURL.getCreateTime());

            if ((new Date().getTime() - shortDate.getTime()) <= 3 * 24 * 3600 * 1000 && (recordInfo == null || recordInfo.getSubmitStatus() == 0)) {
                canSend = -3;
            }

        } catch (ParseException e) {
            logger.error("parse data error", e);
        }


        if (shortURL != null) {
            shortURL.setCreateTime(nowStr);
            shortURL.setShortURL(encode);
            if (canSend == 0) {
                try {
                    shortURL.setStatus("已发送");
                    shortURLService.update(shortURL);
//                    String smsContent = sendMsgUtil.getMsgInfo(domain + "/mobile/" + shortURL.getShortURL());
//                    sendMsgUtil.sendMsg(shortURL.getRealURL(), smsContent);
                    return ReturnData.result(0, "发送短信成功", null);
                } catch (Exception e) {
                    shortURL.setStatus("未发送");
                    shortURLService.update(shortURL);
                    logger.error("短信发送失败", e);
                    return ReturnData.result(-1, "发送短信失败", null);
                }
            } else {
                shortURL.setStatus("未发送");
                shortURLService.update(shortURL);
                if (canSend == -1) {
                    return ReturnData.result(-3, "不满足发送条件：已提交但策略未跑", null);
                } else {
                    return ReturnData.result(-4, "不满足发送条件：以设置为可以更改，无需发送短信", null);
                }
            }
        } else {
            return ReturnData.result(-2, "未找到对应的记录", null);
        }
    }

}
