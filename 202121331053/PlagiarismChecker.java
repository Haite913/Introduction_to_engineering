import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

public class PlagiarismChecker {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("请提供原文文件路径、抄袭版论文文件路径和输出文件路径作为命令行参数");
            return;
        }

        String originalFilePath = args[0];
        String plagiarizedFilePath = args[1];
        String outputFilePath = args[2];

        try {
            long startTime = System.currentTimeMillis(); // 记录程序开始时间

            String originalContent = readFileContent(originalFilePath);
            String plagiarizedContent = readFileContent(plagiarizedFilePath);
            double similarity = calculateSimilarity(originalContent, plagiarizedContent);
            writeResultToFile(outputFilePath, similarity);

            long endTime = System.currentTimeMillis(); // 记录程序结束时间
            long totalTime = endTime - startTime; // 计算函数执行时间
            System.out.println("程序执行时间：" + totalTime + "毫秒");

            System.out.println("重复率计算完成");
        } catch (IOException e) {
            System.out.println("发生错误：" + e.getMessage());
        }
    }

    private static String readFileContent(String filePath) throws IOException {
        long startTime = System.currentTimeMillis(); // 记录函数开始时间

        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));

        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();

        long endTime = System.currentTimeMillis(); // 记录函数结束时间
        long totalTime = endTime - startTime; // 计算函数执行时间
        System.out.println("readFileContent 函数执行时间：" + totalTime + "毫秒");

        return content.toString();
    }

    private static double calculateSimilarity(String originalContent, String plagiarizedContent) {
        long startTime = System.currentTimeMillis(); // 记录函数开始时间

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

        long endTime = System.currentTimeMillis(); // 记录函数结束时间
        long totalTime = endTime - startTime; // 计算函数执行时间
        System.out.println("calculateSimilarity 函数执行时间：" + totalTime + "毫秒");

        return (double) matchingWords / totalWords * 100;
    }

    private static List<String> segmentChineseText(String text) {
        long startTime = System.currentTimeMillis(); // 记录函数开始时间

        Pattern pattern = Pattern.compile("[\\pP\\p{S}\\s]+");

        String cleanText = pattern.matcher(text).replaceAll(" ");
        String[] words = cleanText.split("\\s+");

        List<String> result = new ArrayList<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                result.add(word);
            }
        }

        long endTime = System.currentTimeMillis(); // 记录函数结束时间
        long totalTime = endTime - startTime; // 计算函数执行时间
        System.out.println("segmentChineseText 函数执行时间：" + totalTime + "毫秒");

        return result;
    }

    private static void writeResultToFile(String filePath, double similarity) throws IOException {
        long startTime = System.currentTimeMillis(); // 记录函数开始时间

        FileWriter writer = new FileWriter(filePath);
        writer.write(String.format("%.2f", similarity));
        writer.close();

        long endTime = System.currentTimeMillis(); // 记录函数结束时间
        long totalTime = endTime - startTime; // 计算函数执行时间
        System.out.println("writeResultToFile 函数执行时间：" + totalTime + "毫秒");
    }
}