package com.ese.cloud.client.api;

import java.util.List;

/**
 * Created by wangchengcheng on 2017/11/30.
 */
public class UploadRequest extends BaseMobileRequest {

    private List<ValuePair> valuePairs;

    public List<ValuePair> getValuePairs() {
        return valuePairs;
    }

    public void setValuePairs(List<ValuePair> valuePairs) {
        this.valuePairs = valuePairs;
    }

    public static class ValuePair {

        private long timestamp;

        private String packet;

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getPacket() {
            return packet;
        }

        public void setPacket(String packet) {
            this.packet = packet;
        }
    }
}