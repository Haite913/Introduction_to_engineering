import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PlagiarismChecker {
    public static void main(String[] args) {
		//如果缺少了原文文件路径、抄袭版论文文件路径和输出文件路径中任意一个文件就提前结束
        if (args.length != 3) {
            System.out.println("请提供原文文件路径、抄袭版论文文件路径和输出文件路径作为命令行参数");
            return;
        }
        //保存原文件路径
        String originalFilePath = args[0];
		//保存抄袭文件路径
        String plagiarizedFilePath = args[1];
		//保存输出文件路径
        String outputFilePath = args[2];

        try {
		    //传入文件路径，调用方法，查出原文件内容
            String originalContent = readFileContent(originalFilePath);
            //传入文件路径，调用方法，查出抄袭文件内容
			String plagiarizedContent = readFileContent(plagiarizedFilePath);
            //传入两个文件内容，计算重复率
            double similarity = calculateSimilarity(originalContent, plagiarizedContent);
            //调用方法，将重复率写入输出文件
            writeResultToFile(outputFilePath, similarity);
            System.out.println("重复率计算完成");
        } catch (IOException e) {
            System.out.println("发生错误：" + e.getMessage());
        }
    }

    private static String readFileContent(String filePath) throws IOException {
        //使用StringBuilder拼接字符串，提高效率
		StringBuilder content = new StringBuilder();
        //FileInputStream打开文件输入流，InputStreamReader将字节流转换为字符流，并使用UTF-8字符编码进行解码，BufferedReader逐行读取字符流。
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
        
		String line;
        //循环按行读取文件字符流，直至到达字符流末尾，所有字符流内容追加到字符串后
		while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();
        return content.toString();
    }

    private static double calculateSimilarity(String originalContent, String plagiarizedContent) {
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

        return (double) matchingWords / totalWords * 100;
    }

    private static List<String> segmentChineseText(String text) {
		System.out.println(Arrays.asList(text.split("\\s+|(?<!\\p{L})|(?![\\p{L}\\p{N}])")));
        return Arrays.asList(text.split("\\s+|(?<!\\p{L})|(?![\\p{L}\\p{N}])"));
    }

    private static void writeResultToFile(String filePath, double similarity) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(String.format("%.2f", similarity));
        writer.close();
    }
}