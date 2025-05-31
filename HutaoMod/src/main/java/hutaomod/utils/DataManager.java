package hutaomod.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.megacrit.cardcrawl.core.Settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/*
 * 数据管理类Singleton，能通过CSV读取技能信息
 */
public class DataManager {
    private static DataManager instance = null;

    // set the charset to be able to read chinese
    public static final String charset = "GBK";
    
    public Map<String, String[]> cardData;
    public static final String CARD_CSV_ZHS = "HuTaoResources/localization/zhs/cardData.csv";
    public Map<String, String[]> relicData;
    public static final String RELIC_CSV = "HuTaoResources/localization/zhs/relicData.csv";
    public Map<String, String[]> monsterData;
    public static final String MONSTER_CSV = "HuTaoResources/localization/zhs/monsterData.csv";
    
    private DataManager() {
        cardData = new HashMap<>();
        relicData = new HashMap<>();
        monsterData = new HashMap<>();
        parseCSV(cardData, CARD_CSV_ZHS);
        // parseCSV(relicData, RELIC_CSV);
        // parseCSV(monsterData, MONSTER_CSV);
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public static void parseCSV(Map<String, String[]> data, String filePath) {
        FileHandle fileHandle = Gdx.files.internal(filePath);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fileHandle.read(), charset))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 使用自定义的解析方法
                String[] columns = parseCSVLine(line);
                if (columns.length > 1) { // 确保至少有两列数据
                    String primaryKey = columns[0];
                    if (primaryKey.isEmpty()) continue;
                    String[] values = new String[columns.length - 1];
                    System.arraycopy(columns, 1, values, 0, columns.length - 1);
                    data.put(primaryKey, values);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 格式参照 {@link hutaomod.utils.CardDataCol} 和cardData.csv
     * @param filePath 文件路径
     */
    public static void addCards(String filePath) {
        parseCSV(getInstance().cardData, filePath);
    }

    private static String[] parseCSVLine(String line) {
        // 使用 StringBuilder 来构建当前字段
        StringBuilder field = new StringBuilder();
        boolean inQuotes = false;
        List<String> fields = new ArrayList<>();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes; // 切换引号状态
            } else if (c == ',' && !inQuotes) {
                fields.add(field.toString()); // 字段结束
                field.setLength(0); // 清空字段构建器
            } else {
                field.append(c); // 继续构建当前字段
            }
        }
        // 添加最后一个字段
        fields.add(field.toString());

        return fields.toArray(new String[0]);
    }
    
    public String getCardData(String key, CardDataCol col) {
        String result = cardData.get(key)[col.ordinal()];
        switch (col) {
            case Damage:
            case Block:
            case MagicNumber:
                if (Objects.equals(result, "")) {
                    return 0 + "";
                }
                break;
            case UpgradeDamage:
            case UpgradeBlock:
            case UpgradeMagicNumber:
            case UpgradeCost:
                if (Objects.equals(result, "")) {
                    return NULL_INT + "";
                }
                break;
            case Description:
            case UpgradeDescription:
                return "{@@}" + result;
        }
        return result;
    }
    
    public int getCardDataInt(String key, CardDataCol col) {
        return Integer.parseInt(getCardData(key, col));
    }
    
    public static final int NULL_INT = -9;

    public static class ChineseReplacer {
        public static void main (String[] args) {
            FileTextReplacer.replaceZHS();
        }
    }
    
    public static class FileTextReplacer {
        private static void replaceZHS() {
            // 替换规则
            Map<String, String> replacements = new HashMap<>();
            replacements.put("阴牌", " [yinIcon] 牌");
            replacements.put("阳牌", " [yangIcon] 牌");
            replacements.put("【血梅香】", " [bbIcon] ");
            replacements.put("【死气】", " [siIcon] ");
            replacements.put(" D ", " !D! ");
            replacements.put(" B ", " !B! ");
            replacements.put(" M ", " !M! ");
            replacements.put(" Y ", " {!Y!|0=Y|@=!Y!} ");
            replacements.put(" E ", " [E] ");
            replacements.put("【", " hutaomod:");
            replacements.put("】", " ");
            replacements.put("〖", " ");
            replacements.put("〗", " ");
            replacements.put("『", " *");
            replacements.put("』", " ");
            replacements.put("「", " *「");
            replacements.put("」", "」 ");
            // replacements.put("能量", " [E] ");
            replacements.put("临时生命", " 临时生命 ");
            replacements.put("燃血", " hutaomod:燃血 ");
            replacements.put("弹射", " hutaomod:弹射 ");
            replacements.put("阴盛阳虚", " hutaomod:阴盛阳虚 ");
            replacements.put("阳盛阴虚", " hutaomod:阳盛阴虚 ");
            replacements.put("阴阳平衡", " hutaomod:阴阳平衡 ");
            replacements.put("消耗。", " 消耗 。");
            replacements.put("虚无。", " 虚无 。");
            replacements.put("固有。", " 固有 。");
            replacements.put("保留。", " 保留 。");
            replacements.put("墓地。", " stslib:墓地 。");

            // 读取文件并替换文本
            replaceTextInFile(CARD_CSV_ZHS, replacements);

            replacements.clear();
            replacements.put("  ", " ");

            replaceTextInFile(CARD_CSV_ZHS, replacements);
        }
        
        private static void replaceTextInFile(String filePath, Map<String, String> replacements) {
            filePath = "src/main/resources/" + filePath;
            
            // 读取文件内容，使用 UTF-8 编码
            StringBuilder content = new StringBuilder();
            Path path = Paths.get(filePath);
            try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName(charset))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append(System.lineSeparator());
                }
            } catch (IOException e) {
                System.out.println("读取文件时出错: " + e.getMessage());
                return;
            }

            // 进行文本替换
            String text = content.toString();
            for (Map.Entry<String, String> entry : replacements.entrySet()) {
                text = text.replaceAll(entry.getKey(), entry.getValue());
            }

            // 将替换后的内容写回文件，使用 UTF-8 编码
            try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName(charset))) {
                writer.write(text);
            } catch (IOException e) {
                System.out.println("写入文件时出错: " + e.getMessage());
            }

            System.out.println("文本替换完成并写入文件: " + filePath);
        }
    }
}
