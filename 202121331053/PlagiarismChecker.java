import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

public class PlagiarismChecker {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("���ṩԭ���ļ�·������Ϯ�������ļ�·��������ļ�·����Ϊ�����в���");
            return;
        }

        String originalFilePath = args[0];
        String plagiarizedFilePath = args[1];
        String outputFilePath = args[2];

        try {
            long startTime = System.currentTimeMillis(); // ��¼����ʼʱ��

            String originalContent = readFileContent(originalFilePath);
            String plagiarizedContent = readFileContent(plagiarizedFilePath);
            double similarity = calculateSimilarity(originalContent, plagiarizedContent);
            writeResultToFile(outputFilePath, similarity);

            long endTime = System.currentTimeMillis(); // ��¼�������ʱ��
            long totalTime = endTime - startTime; // ���㺯��ִ��ʱ��
            System.out.println("����ִ��ʱ�䣺" + totalTime + "����");

            System.out.println("�ظ��ʼ������");
        } catch (IOException e) {
            System.out.println("��������" + e.getMessage());
        }
    }

    private static String readFileContent(String filePath) throws IOException {
        long startTime = System.currentTimeMillis(); // ��¼������ʼʱ��

        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));

        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();

        long endTime = System.currentTimeMillis(); // ��¼��������ʱ��
        long totalTime = endTime - startTime; // ���㺯��ִ��ʱ��
        System.out.println("readFileContent ����ִ��ʱ�䣺" + totalTime + "����");

        return content.toString();
    }

    private static double calculateSimilarity(String originalContent, String plagiarizedContent) {
        long startTime = System.currentTimeMillis(); // ��¼������ʼʱ��

        List<String> originalWords = segmentChineseText(originalContent);
        List<String> plagiarizedWords = segmentChineseText(plagiarizedContent);

        Set<String> originalWordSet = new HashSet<>(originalWords);
        int totalWords = originalWords.size();
        int matchingWords = 0;

        for (String word : plagiarizedWords) {
            if (originalWordSet.contains(word)) {
                matchingWords++;
            }
        }

        long endTime = System.currentTimeMillis(); // ��¼��������ʱ��
        long totalTime = endTime - startTime; // ���㺯��ִ��ʱ��
        System.out.println("calculateSimilarity ����ִ��ʱ�䣺" + totalTime + "����");

        return (double) matchingWords / totalWords * 100;
    }

    private static List<String> segmentChineseText(String text) {
        long startTime = System.currentTimeMillis(); // ��¼������ʼʱ��

        Pattern pattern = Pattern.compile("[\\pP\\p{S}\\s]+");

        String cleanText = pattern.matcher(text).replaceAll(" ");
        String[] words = cleanText.split("\\s+");

        List<String> result = new ArrayList<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                result.add(word);
            }
        }

        long endTime = System.currentTimeMillis(); // ��¼��������ʱ��
        long totalTime = endTime - startTime; // ���㺯��ִ��ʱ��
        System.out.println("segmentChineseText ����ִ��ʱ�䣺" + totalTime + "����");

        return result;
    }

    private static void writeResultToFile(String filePath, double similarity) throws IOException {
        long startTime = System.currentTimeMillis(); // ��¼������ʼʱ��

        FileWriter writer = new FileWriter(filePath);
        writer.write(String.format("%.2f", similarity));
        writer.close();

        long endTime = System.currentTimeMillis(); // ��¼��������ʱ��
        long totalTime = endTime - startTime; // ���㺯��ִ��ʱ��
        System.out.println("writeResultToFile ����ִ��ʱ�䣺" + totalTime + "����");
    }
}