package com.atlas.server.utils;

import com.atlas.server.model.Reply;

import java.util.ArrayList;
import java.util.List;

public class ReplayNode {

    private Reply reply;
    private List<ReplayNode> listReply = new ArrayList<>();
    public ReplayNode() {
    }

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    public List<ReplayNode> getListReply() {
        return listReply;
    }

    public void setListReply(List<ReplayNode> listReply) {
        this.listReply = listReply;
    }
}
