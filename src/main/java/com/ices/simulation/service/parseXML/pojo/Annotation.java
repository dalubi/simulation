package com.ices.simulation.service.parseXML.pojo;

public class Annotation {
    int AnnotationId;
    String AnnotationContent;
    String fromFederate;
    String fromInteraction;

    public int getAnnotationId() {
        return AnnotationId;
    }

    public void setAnnotationId(int annotationId) {
        AnnotationId = annotationId;
    }

    public String getAnnotationContent() {
        return AnnotationContent;
    }

    public void setAnnotationContent(String annotationContent) {
        AnnotationContent = annotationContent;
    }

    public String getFromFederate() {
        return fromFederate;
    }

    public void setFromFederate(String fromFederate) {
        this.fromFederate = fromFederate;
    }

    public String getFromInteraction() {
        return fromInteraction;
    }

    public void setFromInteraction(String fromInteraction) {
        this.fromInteraction = fromInteraction;
    }

    public Annotation(int annotationId, String annotationContent, String fromFederate, String fromInteraction) {
        AnnotationId = annotationId;
        AnnotationContent = annotationContent;
        this.fromFederate = fromFederate;
        this.fromInteraction = fromInteraction;
    }

    @Override
    public String toString() {
        return "Annotation{" +
                "AnnotationId=" + AnnotationId +
                ", AnnotationContent='" + AnnotationContent + '\'' +
                ", fromFederate='" + fromFederate + '\'' +
                ", fromInteraction='" + fromInteraction + '\'' +
                '}';
    }
}
