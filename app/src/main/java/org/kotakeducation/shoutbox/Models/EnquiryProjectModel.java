package org.kotakeducation.shoutbox.Models;

import java.io.Serializable;

public class EnquiryProjectModel implements Serializable {

    String question, predict, plan, investigate, record, analyze, connect;

    public EnquiryProjectModel() {
    }

    public EnquiryProjectModel(String question, String predict, String plan, String investigate, String record, String analyze, String connect) {
        this.question = question;
        this.predict = predict;
        this.plan = plan;
        this.investigate = investigate;
        this.record = record;
        this.analyze = analyze;
        this.connect = connect;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPredict() {
        return predict;
    }

    public void setPredict(String predict) {
        this.predict = predict;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getInvestigate() {
        return investigate;
    }

    public void setInvestigate(String investigate) {
        this.investigate = investigate;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getAnalyze() {
        return analyze;
    }

    public void setAnalyze(String analyze) {
        this.analyze = analyze;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }
}
