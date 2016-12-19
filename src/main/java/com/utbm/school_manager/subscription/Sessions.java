/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utbm.school_manager.subscription;

import com.mysite.entity.CourseSession;
import com.mysite.service.CourseSessionService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;


/**
 *
 * @author Rémi
 * 
 * 
 * 
 */
@ManagedBean(name = "sessions")
@ViewScoped
public class Sessions implements Serializable{
    
    private CourseSessionService courseSessionService = new CourseSessionService(CourseSession.class);
    private Map<String,String> sessionCriteria = new HashMap<String,String>();
    private List<CourseSession> sessionList = new ArrayList<CourseSession>();
    private String courseId;
    private String courseTitle;
    private CourseSession selectedCourseSession;

    public Sessions() {
    }

    public List<CourseSession> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<CourseSession> sessionList) {
        this.sessionList = sessionList;
    }
    
    /* I remove @PostConstruct because since we need viewParams to be setted
    before requesting data this method is called in viewAction tag */
    public void init(){
        sessionCriteria.put("course_id", courseId);
        sessionList = courseSessionService.showSessionFromCriteria(sessionCriteria);
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    
    public CourseSessionService getCourseSessionService() {
        return courseSessionService;
    }

    public void setCourseSessionService(CourseSessionService courseSessionService) {
        this.courseSessionService = courseSessionService;
    }

    public Map<String, String> getSessionCriteria() {
        return sessionCriteria;
    }

    public void setSessionCriteria(Map<String, String> sessionCriteria) {
        this.sessionCriteria = sessionCriteria;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public CourseSession getSelectedCourseSession() {
        return selectedCourseSession;
    }

    public void setSelectedCourseSession(CourseSession selectedCourseSession) {
        this.selectedCourseSession = selectedCourseSession;
    }
    
    
    
    public void showRegisterationFrom(){
        
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("resizable", true);
        options.put("draggable", true);
        options.put("modal", true);
        List<String> courseSessionIdValue = new ArrayList<>();
         List<String> courseTitleValue = new ArrayList<>();
         
        courseSessionIdValue.add(""+selectedCourseSession.getId());
        Map<String,List<String>> params = new HashMap<>();
        params.put("courseSessionId",courseSessionIdValue);
        
        courseTitleValue.add(selectedCourseSession.getCourse().getTitle());
        params.put("courseTitle", courseTitleValue);
        RequestContext.getCurrentInstance().openDialog("registerationForm", options, params);
    }
    
    public void onRegisterationComplete(SelectEvent event){
        Boolean registerationStatus = (Boolean) event.getObject();
        if(registerationStatus){
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Registeration successful", "See you!")
                );
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registeration failed", "Please, retry")
                );
        }
    }
}
