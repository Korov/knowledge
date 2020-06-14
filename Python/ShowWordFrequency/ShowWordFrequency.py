from wordcloud import WordCloud
import matplotlib.pyplot as plt
import jieba
import os

'''
对texts文件夹中的所有文件进行统计，生成词频信息
会生成词云保存到pictures中，图片名称为对应的texts中的文件名，并且会生成一份将所有文件内容整合的词云图片
会生成词频的文本文件保存到results中，文本对应的文件名为texts中对应的文件名，并且会生成一份整合所有文件内容的词频结果，对于单词长度为1的跳过统计
'''


# 根据文本生成词云图片保存到pictures中
def savePicture(text, pictureName):
    wordCloud.generate(text)
    wordCloud.to_file('pictures/' + pictureName + '.png')


# 根据文本生成词频保存到results中
def saveResult(text, resultName):
    words = jieba.lcut(text)
    counts = {}
    for word in words:
        # 跳过单词长度为1的单词
        if len(word) == 1:
            continue
        else:
            counts[word] = counts.get(word, 0) + 1

    items = list(counts.items())
    items.sort(key=lambda x: x[1], reverse=True)
    result = ''
    for i in range(len(items)):
        word, count = items[i]
        result = result + "{0:<5}{1:>5}".format(word, count) + '\n'

    resultFile = open('results/' + resultName + '.txt', 'w', encoding='utf-8')
    resultFile.write(result)
    resultFile.close()


def statistics(dir, file, all):
    text = open(dir + '/' + file, 'r').read().lower()
    all = all + text
    pos = file.rfind('.')
    savePicture(text, file[:pos])
    saveResult(text, file[:pos])
    print(file + ' statistic complete!')
    return all


if __name__ == "__main__":
    # 判断pictures和results文件夹是否存在，不存在则创建
    if not os.path.exists('pictures'):
        os.mkdir('pictures')
    if not os.path.exists('results'):
        os.mkdir('results')

    # 使用了微软雅黑字体，防止中文乱码，也可以自己下载对应的字体放到fonts文件夹中并修改font_path的值
    wordCloud = WordCloud(background_color='white', width=800, height=660, margin=2, font_path=r'fonts/msyh.ttf')
    for root, dirs, files in os.walk('texts'):
        print('Scan files from (' + root + ') complete!')

    print('We will generate wordcloud picture for files: \n' + ', '.join(files))

    all = ''
    for file in files:
        all = statistics(root, file, all)

    # 将所整合后的文本生成词云和文本信息
    savePicture(all, 'all')
    saveResult(all, 'all')

    # 将整合后的词云图片展示出来
    plt.imshow(wordCloud)
    plt.axis('off')
    plt.show()
