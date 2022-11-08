import openpyxl

if __name__ == "__main__":
    originFile = 'C:\\Users\\korov\\Downloads\\MITRE ATT&CK   矩阵技术描述列表（中文版)_20220919 - Copy.xlsx'
    workbook = openpyxl.load_workbook(originFile)
    for worksheet in workbook.worksheets:
        cells = []
        for cell in worksheet.merged_cells.ranges:
            if cell.max_col == 4:
                if cell.min_row >= 3:
                    cells.append((cell.min_col, cell.min_row, cell.max_row))

        cells.sort()
        tactics_code = worksheet.cell(column=1, row=3).value.strip()
        tactics_name = worksheet.cell(column=2, row=3).value.strip()
        tactics_description = worksheet.cell(column=3, row=3).value.strip()
        print('INSERT INTO `siem_att_ck_info`(`code`, `parent_code`, `type`, `name_zh`, `description_zh`, `name_en`, `description_en`, `create_time`, `update_time`) VALUES')
        print(
            "('{}', '', 0, '{}', '{}', '', '', NOW(), NOW()),".format(tactics_code, tactics_name, tactics_description))

        for cell_index in range(0, len(cells)):
            cell = cells[cell_index]
            col = cell[0]
            min_row = cell[1]
            max_row = cell[2]

            techniques_code = worksheet.cell(column=col, row=min_row).value.strip()
            techniques_name = worksheet.cell(column=col + 1, row=min_row).value.strip()
            techniques_description = worksheet.cell(column=col + 2, row=min_row).value.strip()
            print("('{}', '{}', 1, '{}', '{}', '', '', NOW(), NOW()),".format(techniques_code, tactics_code,
                                                                              techniques_name, tactics_description))

            for row_index in range(min_row, max_row + 1):
                sub_techniques_code = worksheet.cell(column=col + 3, row=row_index).value.strip()
                sub_techniques_name = worksheet.cell(column=col + 4, row=row_index).value.strip()
                sub_techniques_description = worksheet.cell(column=col + 5, row=row_index).value.strip()

                if row_index == max_row and cell_index == len(cells) - 1:
                    print("('{}', '{}', 2, '{}', '{}', '', '', NOW(), NOW());".format(sub_techniques_code,
                                                                                  techniques_code,
                                                                                  sub_techniques_name,
                                                                                  sub_techniques_description))
                else:
                    print("('{}', '{}', 2, '{}', '{}', '', '', NOW(), NOW()),".format(sub_techniques_code,
                                                                                      techniques_code,
                                                                                      sub_techniques_name,
                                                                                      sub_techniques_description))

        print("\n\n")

    # print('workbook title:{}, max column:{}, max row:{}'.format(worksheet.title, worksheet.max_column,
    #                                                             worksheet.max_row))
