package com.bawnorton.mixinsquared.util;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

public class SimpleMessager implements Messager {
	@Override
	public void printMessage(Diagnostic.Kind kind, CharSequence msg) {
		printMessage(kind, msg, null, null);
	}

	@Override
	public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element element) {
		printMessage(kind, msg, element, null);
	}

	@Override
	public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element element, AnnotationMirror annotationMirror) {
		printMessage(kind, msg, element, annotationMirror, null);
	}

	@Override
	public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element element, AnnotationMirror annotationMirror, AnnotationValue annotationValue) {
		System.out.printf("[%s] %s%n", kind.name(), msg);
	}
}
