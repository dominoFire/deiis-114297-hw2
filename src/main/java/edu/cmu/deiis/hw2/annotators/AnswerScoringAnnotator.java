package edu.cmu.deiis.hw2.annotators;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

public class AnswerScoringAnnotator extends JCasAnnotator_ImplBase {

	@Override
	//asumiremos una pregunta y varias respuestas por archivo
	public void process(JCas aJCas)
			throws AnalysisEngineProcessException 
	{
		// TODO Auto-generated method stub
		
	}
}
