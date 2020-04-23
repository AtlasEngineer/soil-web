package com.atlas.server.utils;

import com.atlas.server.model.Answer;
import com.atlas.server.model.Reply;

import javax.jms.Topic;
import java.util.ArrayList;
import java.util.List;

public class AnswerNode {

    private Answer Answer;
    private List<ReplayNode> replays = new ArrayList<>();

    public com.atlas.server.model.Answer getAnswer() {
        return Answer;
    }

    public void setAnswer(com.atlas.server.model.Answer answer) {
        Answer = answer;
    }

    public AnswerNode() {
    }

    public List<ReplayNode> getReplays() {
        return replays;
    }
    public void setReplays(List<ReplayNode> replays) {
        this.replays = replays;
    }




}
