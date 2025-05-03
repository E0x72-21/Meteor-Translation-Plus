import re

def calculate_progress(filename):

    # 进度统计
    def count_matches(pattern, file_category, status):
        # file_name = f"{file_category}_{status}.txt"
        match_count = 0
        Pattern = re.compile(pattern)

        with open(filename, 'r', encoding='utf-8') as file:
            # if file_category != "overall":
            #     with open(file_name, "w", encoding="utf-8") as f:
            #         for line_number, line in enumerate(file, start=1):
            #             if Pattern.search(line):
            #                 match_count += 1
            #                 f.write(line)
            # else:
                for line_number, line in enumerate(file, start=1):
                    if Pattern.search(line):
                        match_count += 1
        return match_count

    # 进度计算
    def compute_progress(total_pattern, todo_pattern, category):
        todo = count_matches(todo_pattern, category, "todo")
        total = count_matches(total_pattern, category, "total")
        # print(f"{category}-{todo} todo")
        # print(f"{category}-{total} total")
        if total == 0:
            return 100.0  # 防止除以0
        return round(100 - 100.0 * todo / total, 2)

    # 各进度赋值
    rejectsprogress = compute_progress(r'meteor_rejects.*?description":\s*"', r'meteor_rejects.*?description":\s*""', "rejects")
    meteorplusprogress = compute_progress(r'meteor\+.*?description":\s*"', r'meteor\+.*?description":\s*""', "plus")
    translationprogress = compute_progress(r'meteor_translation_addon.*?description":\s*"', r'meteor_translation_addon.*?description":\s*""', "translation")
    meteorprogress = compute_progress(r'meteor_client.*?description":\s*"', r'meteor_client.*?description":\s*""', "meteor")
    overallprogress = compute_progress(r'description":\s*"', r'description":\s*""', "overall")

    # 输出各翻译进度
    with open("progress.txt", "w", encoding="utf-8") as f:
        f.write(f"- Meteor进度：{meteorprogress}%\n- Meteor rejects进度：{rejectsprogress}%\n- Meteor+进度：{meteorplusprogress}%\n- Meteor translation+进度：{translationprogress}%\n- 总进度：{overallprogress}%")

calculate_progress('zh_cn-translating.json')