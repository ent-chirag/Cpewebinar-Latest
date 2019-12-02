package com.myCPE.model.webinar_details;

import com.google.gson.annotations.SerializedName;

public class WebinarQuestionsItem {

    @SerializedName("question")
    private String question;

    @SerializedName("wrong_answer_note_a")
    private Object wrongAnswerNoteA;

    @SerializedName("currect_answer_note_b")
    private String currectAnswerNoteB;

    @SerializedName("currect_answer_note_a")
    private String currectAnswerNoteA;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("type")
    private String type;

    @SerializedName("wrong_answer_note_d")
    private Object wrongAnswerNoteD;

    @SerializedName("wrong_answer_note_c")
    private Object wrongAnswerNoteC;

    @SerializedName("currect_answer_note_d")
    private String currectAnswerNoteD;

    @SerializedName("wrong_answer_note_b")
    private Object wrongAnswerNoteB;

    @SerializedName("currect_answer_note_c")
    private String currectAnswerNoteC;

    @SerializedName("option_d")
    private String optionD;

    @SerializedName("option_b")
    private String optionB;

    @SerializedName("option_c")
    private String optionC;

    @SerializedName("answer")
    private String answer;

    @SerializedName("updated_at")
    private Object updatedAt;

    @SerializedName("added_by")
    private int addedBy;

    @SerializedName("webinar_id")
    private int webinarId;

    @SerializedName("updated_by")
    private Object updatedBy;

    @SerializedName("id")
    private int id;

    @SerializedName("time")
    private String time;

    @SerializedName("option_a")
    private String optionA;

    @SerializedName("status")
    private String status;

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setWrongAnswerNoteA(Object wrongAnswerNoteA) {
        this.wrongAnswerNoteA = wrongAnswerNoteA;
    }

    public Object getWrongAnswerNoteA() {
        return wrongAnswerNoteA;
    }

    public void setCurrectAnswerNoteB(String currectAnswerNoteB) {
        this.currectAnswerNoteB = currectAnswerNoteB;
    }

    public String getCurrectAnswerNoteB() {
        return currectAnswerNoteB;
    }

    public void setCurrectAnswerNoteA(String currectAnswerNoteA) {
        this.currectAnswerNoteA = currectAnswerNoteA;
    }

    public String getCurrectAnswerNoteA() {
        return currectAnswerNoteA;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setWrongAnswerNoteD(Object wrongAnswerNoteD) {
        this.wrongAnswerNoteD = wrongAnswerNoteD;
    }

    public Object getWrongAnswerNoteD() {
        return wrongAnswerNoteD;
    }

    public void setWrongAnswerNoteC(Object wrongAnswerNoteC) {
        this.wrongAnswerNoteC = wrongAnswerNoteC;
    }

    public Object getWrongAnswerNoteC() {
        return wrongAnswerNoteC;
    }

    public void setCurrectAnswerNoteD(String currectAnswerNoteD) {
        this.currectAnswerNoteD = currectAnswerNoteD;
    }

    public String getCurrectAnswerNoteD() {
        return currectAnswerNoteD;
    }

    public void setWrongAnswerNoteB(Object wrongAnswerNoteB) {
        this.wrongAnswerNoteB = wrongAnswerNoteB;
    }

    public Object getWrongAnswerNoteB() {
        return wrongAnswerNoteB;
    }

    public void setCurrectAnswerNoteC(String currectAnswerNoteC) {
        this.currectAnswerNoteC = currectAnswerNoteC;
    }

    public String getCurrectAnswerNoteC() {
        return currectAnswerNoteC;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setAddedBy(int addedBy) {
        this.addedBy = addedBy;
    }

    public int getAddedBy() {
        return addedBy;
    }

    public void setWebinarId(int webinarId) {
        this.webinarId = webinarId;
    }

    public int getWebinarId() {
        return webinarId;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return
                "WebinarQuestionsItem{" +
                        "question = '" + question + '\'' +
                        ",wrong_answer_note_a = '" + wrongAnswerNoteA + '\'' +
                        ",currect_answer_note_b = '" + currectAnswerNoteB + '\'' +
                        ",currect_answer_note_a = '" + currectAnswerNoteA + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",type = '" + type + '\'' +
                        ",wrong_answer_note_d = '" + wrongAnswerNoteD + '\'' +
                        ",wrong_answer_note_c = '" + wrongAnswerNoteC + '\'' +
                        ",currect_answer_note_d = '" + currectAnswerNoteD + '\'' +
                        ",wrong_answer_note_b = '" + wrongAnswerNoteB + '\'' +
                        ",currect_answer_note_c = '" + currectAnswerNoteC + '\'' +
                        ",option_d = '" + optionD + '\'' +
                        ",option_b = '" + optionB + '\'' +
                        ",option_c = '" + optionC + '\'' +
                        ",answer = '" + answer + '\'' +
                        ",updated_at = '" + updatedAt + '\'' +
                        ",added_by = '" + addedBy + '\'' +
                        ",webinar_id = '" + webinarId + '\'' +
                        ",updated_by = '" + updatedBy + '\'' +
                        ",id = '" + id + '\'' +
                        ",time = '" + time + '\'' +
                        ",option_a = '" + optionA + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}