package edu.cmu.deiis.hw2.annotators;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import edu.cmu.deiis.types.*;

import java.util.*;


public class AnswerScoringAnnotator
	extends JCasAnnotator_ImplBase 
{

	@Override
	//asumiremos una pregunta y varias respuestas por archivo
	public void process(JCas aJCas)
			throws AnalysisEngineProcessException 
	{
		AnnotationIndex<Annotation> aiq = aJCas.getAnnotationIndex(Question.type);
		AnnotationIndex<Annotation> aia = aJCas.getAnnotationIndex(Answer.type);
		AnnotationIndex<Annotation> ain = aJCas.getAnnotationIndex(NGram.type);
		FSIterator<Annotation> qit = aiq.iterator();
		FSIterator<Annotation> ait = aia.iterator();
		FSIterator<Annotation> nit = ain.iterator();
		NGram ngram = null;
		//vemos de donde vienen los ngrams
		ArrayList<NGram> question_ngrams = new ArrayList<NGram>();
		ArrayList<NGram> answer_ngrams = new ArrayList<NGram>();
		while(nit.hasNext()) {
			ngram = (NGram)nit.next();
			if(ngram.getElementType().equals("Question")) {
				question_ngrams.add(ngram);
			} else if(ngram.getElementType().equals("Answer")) {
				answer_ngrams.add(ngram);
			} else {
				System.err.println("A weird NGram appeared");
			}
		}
		//luego, ver a que respuesta pertenecen
		Answer ans = null;
		ArrayList<NGram> curr_ans_ngrams = new ArrayList<NGram>();
		int i = 0;
		double score = 0;
		AnswerScore ans_score = null;
		while(ait.hasNext()) {
			ans = (Answer)ait.next();
			curr_ans_ngrams.clear();
			while(i<answer_ngrams.size() && inAnnotation(ans, answer_ngrams.get(i))) {
				curr_ans_ngrams.add(answer_ngrams.get(i));
			}
		}
		score = scoreQuestionAnswer(question_ngrams, curr_ans_ngrams);
		//creamos la anotacion del score
		ans_score = new AnswerScore(aJCas);
		ans_score.setBegin(ans.getBegin());
		ans_score.setEnd(ans.getEnd());
		ans_score.setAnswer(ans);
		ans_score.setScore(score);
	}
	
	private double scoreQuestionAnswer(ArrayList<NGram> question_ngrams, ArrayList<NGram> answer_ngrams)
	{
		return 0.;
	}
	
	/**
	 * Determines whether the big annotation contains the little annotation
	 * @param big
	 * @param little
	 * @return
	 */
	private boolean inAnnotation(Annotation big, Annotation little)
	{
		return big.getBegin() <= little.getBegin() && little.getEnd() <= big.getEnd();
	}
}
