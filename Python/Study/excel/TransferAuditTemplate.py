import re

import openpyxl

if __name__ == "__main__":
    originFile = 'C:\\Users\\korov\\Desktop\\temp\\SIEM操作审计_en.xlsx'
    workbook = openpyxl.load_workbook(originFile)
    worksheet = workbook["Sheet2"]

    # columnIndex = 49
    # cellValue = worksheet.cell(columnIndex, 1).value
    # originTemplate = str(cellValue)
    # transferTemplate = originTemplate
    # matchResult = re.findall(r'(?<=[ "])\$\w+(?=[ ":.])', originTemplate, re.UNICODE | re.MULTILINE)
    # for resultIndex, result in enumerate(matchResult):
    #     transferTemplate = transferTemplate.replace(result, "{" + str(resultIndex) + "}", 1)
    # print(f"index:{columnIndex}, transfer: {transferTemplate}")

    keyIndex = 1
    for columnIndex in range(1, worksheet.max_row):
        cellValue = worksheet.cell(columnIndex, 1).value
        if cellValue is None:
            # print(f"index:{columnIndex} is none continue")
            continue
        originTemplate = str(cellValue)
        origin = originTemplate
        # en
        originTemplate = re.sub(r'((?<=(: ))|(?<=(:  )))[\u4e00-\u9fa5]+[段包]1.*', '$multipleValue', originTemplate)
        originTemplate = re.sub(r'(("\$?(name|group)\d")(,|、)?){3}（...）', '$multipleSplitValue', originTemplate)

        # zh
        # originTemplate = re.sub(r'((?<=(：))|(?<=(:  )))[\u4e00-\u9fa5]+[段包]1.*', '$multipleValue', originTemplate)
        # originTemplate = re.sub(r'((“\$?(name|group|tag)\d”)(,|、|，)?){3}（...）', '$multipleSplitValue', originTemplate)

        originTemplate = re.sub(r'((\$?(name|tag)\d)、?){3}（...）', '$multipleSplitValue', originTemplate)
        transferTemplate = originTemplate

        # en
        matchResult = re.findall(r'((?<=[ ">/])\$\w+((?=[-/ ":.$])|($)))|(【[\w/]*】)', originTemplate, re.UNICODE | re.MULTILINE)

        # zh
        # matchResult = re.findall(r'(\$\w+((?=[\u4e00-\u9fa5-/ ，、”:。$])|($)))|(【.*】)', originTemplate, re.UNICODE | re.MULTILINE)
        for index, results in enumerate(matchResult):
            for result in results:
                if result != '':
                    transferTemplate = transferTemplate.replace(result, "{" + str(index) + "}", 1)
        str(keyIndex).zfill(5)
        # print(f"index:{columnIndex}, origin: {originTemplate}")
        # print(f"index:{columnIndex}, transfer: {transferTemplate}")
        print(f"index:{str(keyIndex).zfill(5)}, origin: {origin}, transfer: {transferTemplate}")
        # print(f"{str(keyIndex).zfill(5)} = {transferTemplate}")
        keyIndex = keyIndex + 1
