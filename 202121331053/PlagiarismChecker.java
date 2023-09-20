import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PlagiarismChecker {
    public static void main(String[] args) {
        //���ȱ����ԭ���ļ�·������Ϯ�������ļ�·��������ļ�·��������һ���ļ�����ǰ����
        if (args.length != 3) {
            System.out.println("���ṩԭ���ļ�·������Ϯ�������ļ�·��������ļ�·����Ϊ�����в���");
            return;
        }
        //����ԭ�ļ�·��
        String originalFilePath = args[0];
        //���泭Ϯ�ļ�·��
        String plagiarizedFilePath = args[1];
        //��������ļ�·��
        String outputFilePath = args[2];

        try {
            //�����ļ�·�������÷��������ԭ�ļ�����
            String originalContent = readFileContent(originalFilePath);
            //�����ļ�·�������÷����������Ϯ�ļ�����
            String plagiarizedContent = readFileContent(plagiarizedFilePath);
            //���������ļ����ݣ������ظ���
            double similarity = calculateSimilarity(originalContent, plagiarizedContent);
            //���÷��������ظ���д������ļ�
            writeResultToFile(outputFilePath, similarity);
            System.out.println("�ظ��ʼ������");
        } catch (IOException e) {
            System.out.println("��������" + e.getMessage());
        }
    }

    private static String readFileContent(String filePath) throws IOException {
        //ʹ��StringBuilderƴ���ַ��������Ч��
        StringBuilder content = new StringBuilder();
        //FileInputStream���ļ���������InputStreamReader���ֽ���ת��Ϊ�ַ�������ʹ��UTF-8�ַ�������н��룬BufferedReader���ж�ȡ�ַ�����
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));

        String line;
        //ѭ�����ж�ȡ�ļ��ַ�����ֱ�������ַ���ĩβ�������ַ�������׷�ӵ��ַ�����
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();
        return content.toString();
    }

    private static double calculateSimilarity(String originalContent, String plagiarizedContent) {
        //������ԭ�ĺͳ�Ϯ���������ݰ����źͿո񻮷�Ϊ����
        List<String> originalWords = segmentChineseText(originalContent);
        List<String> plagiarizedWords = segmentChineseText(plagiarizedContent);

        //��ԭ�ĵ�����ӵ�HashSet�У���ΪHashSet�в����ظ�Ԫ��
        Set<String> originalWordSet = new HashSet<>(originalWords);
        int totalWords = originalWords.size();
        int matchingWords = 0;

        //����һ��������ʼֵΪ0�����ڼ�¼ƥ��ĵ�������ѭ������Ϯ�������е���������ԭ�ĵ���ƥ�䣬ÿƥ��һ������ֵ�ͼ�1
        for (String word : plagiarizedWords) {
            if (originalWordSet.contains(word)) {
                matchingWords++;
            }
        }
        //ƥ�䵥������/�ܵ�������=������
        return (double) matchingWords / totalWords * 100;
    }

    private static List<String> segmentChineseText(String text) {
        System.out.println(new ArrayList<>(Arrays.asList(text.split("\\s+|(?=\\p{Punct})|(?<=\\p{Punct})"))));
        return new ArrayList<>(Arrays.asList(text.split("\\s+|(?=\\p{Punct})|(?<=\\p{Punct})")));
    }

    private static void writeResultToFile(String filePath, double similarity) throws IOException {
        //����FileWriter���������ڽ�����д��ָ�����ļ�
        FileWriter writer = new FileWriter(filePath);
        //�������ʱ�����λС��ת�����ַ�����ʽ��д�뵽�ļ���
        writer.write(String.format("%.2f", similarity));
        //�ر��ļ������
        writer.close();
    }
}