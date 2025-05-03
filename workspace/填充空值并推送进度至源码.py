import json

# 打开各文件
with open("en_us.json", "r", encoding="utf-8") as f:
    en_data = json.load(f)
with open("zh_cn-translating.json", "r", encoding="utf-8") as f:
    zh_data = json.load(f)

# 遍历原文key
for key, value in en_data.items():
    if key in zh_data and zh_data[key].strip() == "":
        zh_data[key] = value  # 用英文原文填入

# 保存处理后的zh_cn.json
with open("../src/main/resources/assets/meteor-translation-addon/lang/zh_cn.json", "w", encoding="utf-8") as f:
    json.dump(zh_data, f, ensure_ascii=False, indent=2)