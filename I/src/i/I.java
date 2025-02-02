/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package i;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class I {
    public static  void search (){
         int x;
       Scanner sc = new Scanner(System.in);
    do{
          System.out.println("The  Search Menu Is:");
          System.out.println("1- Boolean Search");
          System.out.println("2- Statical Model");
          System.out.println("3- Exit");
         
            x=sc.nextInt();
          switch(x){
              case 1:
                  booleansearch ();
                  break;
                 case 2:
                  statical();
                  break;
                  default:
                      System.out.println("Finshed");}
      }
                  while((x>0)&&(x<3));
         
    }
    
    

    public static void booleansearch() {
        String doc1_path = "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D1.txt";
        String doc2_path = "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D2.txt";
        String doc3_path = "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D3.txt";
        String doc4_path = "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D4.txt";
        
        String doc1 = readFile(doc1_path);
        String doc2 = readFile(doc2_path);
        String doc3 = readFile(doc3_path);
        String doc4 = readFile(doc4_path);
        Map<String, String> documents = new HashMap<>();
        documents.put("doc1", doc1);
        documents.put("doc2", doc2);
        documents.put("doc3", doc3);
        documents.put("doc4", doc4);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter search Query (use AND, OR, NOT operators):");
        String query = sc.nextLine().toUpperCase();
        String[] queryTerms = query.split("\\s+");
        List<String> searchResults = new ArrayList<>();
        switch (queryTerms[1]) {
            case "AND":
                searchResults = booleanSearchAND(documents, new String[]{queryTerms[0], queryTerms[2]});
                break;
            case "OR":
                searchResults = booleanSearchOR(documents, new String[]{queryTerms[0], queryTerms[2]});
                break;
            case "NOT":
                searchResults = booleanSearchNOT(documents, new String[]{queryTerms[0], queryTerms[2]});
                break;
            default:
                System.out.println("Invalid operator");
                break;
        }
        System.out.println("Documents containing the query: " + searchResults);
    }
    
    public static List<String> booleanSearchAND(Map<String, String> documents, String[] query) {
        List<String> results = new ArrayList<>();
        for (Map.Entry<String, String> entry : documents.entrySet()) {
            String text = entry.getValue().toUpperCase();
            boolean containsAllTerms = true;
            for (String term : query) {
                if (!text.contains(term)) {
                    containsAllTerms = false;
                    break;
                }
            }
            if (containsAllTerms) {
                results.add(entry.getKey());
            }
        }
        return results;
    }

    public static List<String> booleanSearchOR(Map<String, String> documents, String[] query) {
        List<String> results = new ArrayList<>();
        for (Map.Entry<String, String> entry : documents.entrySet()) {
            String text = entry.getValue().toUpperCase();
            boolean containsAnyTerm = false;
            for (String term : query) {
                if (text.contains(term)) {
                    containsAnyTerm = true;
                    break;
                }
            }
            if (containsAnyTerm) {
                results.add(entry.getKey());
            }
        }
        return results;
    }

       public static List<String> booleanSearchNOT(Map<String, String> documents, String[] query) {
    List<String> results = new ArrayList<>();
   
    if (query.length < 2) {
        System.out.println("Invalid query: Not enough terms");
        return results;
    }
    for (Map.Entry<String, String> entry : documents.entrySet()) {
        String text = entry.getValue().toUpperCase();
        boolean containsFirstTerm = text.contains(query[0]);
        boolean containsThirdTerm = text.contains(query[1]);
        if (containsFirstTerm && !containsThirdTerm) {
            results.add(entry.getKey());
        }
    }
    return results;
}

        public static void statical(){
        List<String> documents = new ArrayList<>();
       
        String doc1_path = "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D1.txt";
        String doc2_path = "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D2.txt";
        String doc3_path = "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D3.txt";
        String doc4_path = "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D4.txt";
        
        String doc1 = readFile(doc1_path);
        String doc2 = readFile(doc2_path);
        String doc3 = readFile(doc3_path);
        String doc4 = readFile(doc4_path);
        
        documents.add(doc1);
        documents.add( doc2);
        documents.add( doc3);
        documents.add( doc4);
        
       
        Map<String, Integer> query = new HashMap<>();
        query.put("DOG", 5);
        query.put("WHITE", 2);
        List<String> rankedDocuments = rankDocuments(query, documents);
        System.out.println("Ranked Documents:");
        for (String doc : rankedDocuments) {
            System.out.println(doc);
        }
      }
          
       public static List<String> rankDocuments(Map<String, Integer> query, List<String> documents) {
        Map<String, Integer> documentScores = new HashMap<>();
        for (String doc : documents) {
            int score = 0;
            for (String word : query.keySet()) {
                int frequency = calculateTermFrequency(doc, word);
                score += frequency * query.get(word);
            }
            documentScores.put(doc, score);
        }

        List<Map.Entry<String, Integer>> sortedDocuments = new ArrayList<>(documentScores.entrySet());
        sortedDocuments.sort((d1, d2) -> d2.getValue().compareTo(d1.getValue()));

        List<String> rankedDocuments = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sortedDocuments) {
            rankedDocuments.add(entry.getKey());
        }

        return rankedDocuments;
    }

    public static int calculateTermFrequency(String content, String word) {
        int frequency = 0;
        String[] words = content.split("\\s+");
        for (String w : words) {
            if (w.equalsIgnoreCase(word)) {
                frequency++;
            }
        }
        return frequency;
    }

    
        public static String readFile(String doc_path) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(doc_path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
         public static double calculateSimilarity(Map<String, Integer> vectorA, Map<String, Integer> vectorB) {
        double dotProduct = 0;
        double magnitudeA = 0;
        double magnitudeB = 0;
        for (String word : vectorA.keySet()) {
            if (vectorB.containsKey(word)) {
                dotProduct += vectorA.get(word) * vectorB.get(word);
            }
            magnitudeA += Math.pow(vectorA.get(word), 2);
        }
        for (String word : vectorB.keySet()) {
            magnitudeB += Math.pow(vectorB.get(word), 2);
        }

        if (magnitudeA != 0 && magnitudeB != 0) {
            double similarity = dotProduct / (Math.sqrt(magnitudeA) * Math.sqrt(magnitudeB));
            return similarity;
        } else {
            return 0;
        }
    }
         public static Map<String, Integer> change_text_to_vector(String text) {
        Map<String, Integer> vector = new HashMap<>();
        String[] words = text.toUpperCase().split("\\s+"); 
        for (String word : words) {
            if (!word.isEmpty()) { 
                vector.put(word, vector.getOrDefault(word, 0) + 1);
            }
        }
        return vector;
    }
        
      public static void cos(){
        String[] docPaths = {
                "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D1.txt",
                "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D2.txt",
                "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D3.txt",
                "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D4.txt"
        };

        Map<String, Integer>[] documentVectors = new HashMap[docPaths.length];
        for (int i = 0; i < docPaths.length; i++) {
            String content = readFile(docPaths[i]);
            documentVectors[i] = change_text_to_vector(content);
        }

        for (int i = 0; i < documentVectors.length; i++) {
            for (int j = i + 1; j < documentVectors.length; j++) {
                double similarity = calculateSimilarity(documentVectors[i], documentVectors[j]);
                System.out.println("Cosine Similarity between Document "+(i+1) +" and Document "+(j+1)+"="+ similarity);
            }
        }  
      }
      public static void jaccard(){
       String[] docPaths = {
            "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D1.txt",
            "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D2.txt",
            "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D3.txt",
            "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D4.txt"
    };

    Map<String, Integer>[] documentVectors = new HashMap[docPaths.length];
    for (int i = 0; i < docPaths.length; i++) {
        String content = readFile(docPaths[i]);
        documentVectors[i] = change_text_to_vector(content);
    }

    for (int i = 0; i < documentVectors.length; i++) {
        for (int j = i + 1; j < documentVectors.length; j++) {
            double jaccardSimilarity = calculateJaccardSimilarity(documentVectors[i], documentVectors[j]);
            System.out.println("Jaccard Similarity between Document " + (i + 1) + " and Document " + (j + 1) + " = " + jaccardSimilarity);
        }
    }
}
      public static double calculateJaccardSimilarity(Map<String, Integer> vectorA, Map<String, Integer> vectorB) {
        Set<String> keysA = vectorA.keySet();
        Set<String> keysB = vectorB.keySet();

        Set<String> union = new HashSet<>(keysA);
        union.addAll(keysB);

        Set<String> intersection = new HashSet<>(keysA);
        intersection.retainAll(keysB);

        double intersectionSize = (double) intersection.size();
        double unionSize = (double) union.size();

        if (unionSize != 0) {
            return intersectionSize / unionSize;
        } else {
            return 0.0;
        }
    }

    public static void measure(){
         int x;
       Scanner sc = new Scanner(System.in);
    do{
          System.out.println("The  measure Menu Is:");
          System.out.println("1- Cosine Similarity");
          System.out.println("2- Jaccard Method");
          System.out.println("3- Exit");
         
            x=sc.nextInt();
          switch(x){
              case 1:
                  cos ();
                  break;
                 case 2:
                  jaccard();
                  break;
                  default:
                      System.out.println("Finshed");}
      }
                  while((x>0)&&(x<3));
        
        }
    public static void evaluate(){
        List<String> retrievalDocuments = Arrays.asList("doc1", "doc2", "doc3", "doc4", "doc5", "doc6", "doc7", "doc8", "doc9", "doc10");
        List<String> relevantDocuments = Arrays.asList("doc1", "doc2", "doc6", "doc9");
        calculations(retrievalDocuments, relevantDocuments);

    }

    public static void calculations(List<String> retrievalDocuments, List<String> relevantDocuments) {
        int count = 0;
        int location=0;
        for (int i=0;i<retrievalDocuments.size();i++) {
            if (relevantDocuments.contains(retrievalDocuments.get(i))) {
                count++;
                location = (i+1)+location ;
                double p= (double) count / (i+1); 
                double r=(double) count / relevantDocuments.size();
                double f=2 * (p * r) / (p + r);
    
                System.out.println("Precision for document"+(i+1) +"= "+ p ); 
                System.out.println("Recall for document"+(i+1) +"= " + r);
                System.out.println("f for document"+(i+1) +"= " + f);
            }
        }
        double pr = location / Math.pow(relevantDocuments.size(), 2);
        System.out.println("RankPower for documents= "+ pr );
        
    }
     public static void rank(){
        int x;
       Scanner sc = new Scanner(System.in);
    do{
          System.out.println("Ranked Documents by:");
          System.out.println("1- Query Likelihood Model");
          System.out.println("2- Jelinek-Mercer smoothing ");
          System.out.println("3- Exit");
         
            x=sc.nextInt();
          switch(x){
              case 1:
                  QueryLikelihoodModel ();
                  break;
              case 2:
                  jsmoothing();
              
                  break;
                   default:
                      System.out.println("Finshed");
          }
    }
                  while((x>0)&&(x<3));   }
     public static void QueryLikelihoodModel() {
           
       List<String> documents = new ArrayList<>();
       
        String doc1_path = "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D1.txt";
        String doc2_path = "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D2.txt";
        String doc3_path = "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D3.txt";
        String doc4_path = "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D4.txt";
        
        String doc1 = readFile(doc1_path);
        String doc2 = readFile(doc2_path);
        String doc3 = readFile(doc3_path);
        String doc4 = readFile(doc4_path);
        
        documents.add(doc1);
        documents.add( doc2);
        documents.add( doc3);
        documents.add( doc4);
       
       List<String> query = Arrays.asList("DOG", "BLACK");

        List<String> rankedDocuments = rankDocuments(query, documents);

        
        System.out.println("Ranked Documents:");
        for (String doc : rankedDocuments) {
            System.out.println(doc);
        }
    }
     public static List<String> rankDocuments(List<String> query, List<String> documents) {
        Map<String, Double> documentScores = new HashMap<>();
        for (String doc : documents) {
            double score = calculateQueryLikelihood(query, doc);
            documentScores.put(doc, score);
        }

        
        List<String> rankedDocuments = new ArrayList<>(documentScores.keySet());
        rankedDocuments.sort((d1, d2) -> Double.compare(documentScores.get(d2), documentScores.get(d1)));

        return rankedDocuments;
    }
     private static double calculateQueryLikelihood(List<String> query, String document) {
       
        String[] words = document.split("\\s+");
        int totalWords = words.length;

        
        Map<String, Integer> termFrequency = new HashMap<>();
        for (String word : words) {
            termFrequency.put(word.toUpperCase(), termFrequency.getOrDefault(word.toUpperCase(), 0) + 1);
        }

        
        double likelihood = 1.0;
        for (String word : query) {
            int count = termFrequency.getOrDefault(word.toUpperCase(), 0);
            likelihood *= (double) count / totalWords;
        }

        return likelihood;
    }

     
     public static void jsmoothing(){
         String[] docPaths = {
                "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D1.txt",
                "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D2.txt",
                "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D3.txt",
                "C:\\Users\\ALSERAG FOR LAPTOP\\Downloads\\Randoum_Documents\\D4.txt"
                  };
        
                 Map<String, Integer>[] documentVectors = new HashMap[docPaths.length];
                 for (int i = 0; i < docPaths.length; i++) {
                 String content = readFile(docPaths[i]);
                 documentVectors[i] = change_text_to_vector(content);
                 }

                 Scanner scanner = new Scanner(System.in);
                 System.out.println("Enter your query:");
                 String query = scanner.nextLine();
                 System.out.println("Enter lambda value for Jelinek-Mercer smoothing:");
                 double lambda = scanner.nextDouble();

                 rankDocuments(documentVectors, query, lambda);
                 
         
     }
    
    
    public static void rankDocuments(Map<String, Integer>[] documentVectors, String query, double lambda) {
        Map<String, Double> documentScores = new HashMap<>();

        for (int i = 0; i < documentVectors.length; i++) {
            Map<String, Integer> documentVector = documentVectors[i];
            double score = jelinekMercerSmoothing(documentVectors, query, lambda, documentVector);
            documentScores.put("Document " + (i + 1), score);
        }

        List<Map.Entry<String, Double>> sortedDocuments = new ArrayList<>(documentScores.entrySet());
        sortedDocuments.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        System.out.println("Ranked Documents:");
        for (Map.Entry<String, Double> entry : sortedDocuments) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
   
    public static double jelinekMercerSmoothing(Map<String, Integer>[] documentVectors, String query, double lambda, Map<String, Integer> documentVector) {
        double smoothing = 0.0;
        for (String term : query.split("\\s+")) {
            double termProbability = 0.0;
            int termFrequencyInDocument = documentVector.getOrDefault(term, 0);
            int totalWordsInDocument = documentVector.values().stream().mapToInt(Integer::intValue).sum();
            if (totalWordsInDocument != 0) {
                termProbability = (1 - lambda) * (double) termFrequencyInDocument / totalWordsInDocument;
            }

            int termFrequencyInCollection = 0;
            for (Map<String, Integer> docVector : documentVectors) {
                termFrequencyInCollection += docVector.getOrDefault(term, 0);
            }

            double collectionProbability = (lambda * (double) termFrequencyInCollection) / getTotalWordCount(documentVectors);
            smoothing += Math.log(termProbability + collectionProbability);
        }
        return smoothing;
    }

    public static int getTotalWordCount(Map<String, Integer>[] documentVectors) {
        int totalWords = 0;
        for (Map<String, Integer> documentVector : documentVectors) {
            totalWords += documentVector.values().stream().mapToInt(Integer::intValue).sum();
        }
        return totalWords;
    }
    
    public static void main(String[] args) {
        
        int x;
       Scanner sc = new Scanner(System.in);
    do{
          System.out.println("The Menu Is:");
          System.out.println("1- Search");
          System.out.println("2- Measure");
          System.out.println("3- Rank");
          System.out.println("4- Evaluate");
          System.out.println("5- Exit");
          System.out.println("Enter Your Choice :");
            x=sc.nextInt();
          switch(x){
              case 1:
                  search ();
                  break;
                 case 2:
                  measure();
                  break;
                 case 3:
                  rank();
                   break;
                   case 4:
                   evaluate();
                      break; 
                      default:
                      System.out.println("Finshed");}
      }
                  while((x>0)&&(x<5));
         
    }    
    }
    


