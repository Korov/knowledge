if __name__ == "__main__":
    str1 = 'ab,cde,fgh,ijk'
    str2 = ','
    str3 = str1[0:str1.find(str2)]
    print(str3)
    str1 = str1[str1.find(str2) + 1:]
    print(str1)

    str1 = 'acc'
    str2 = ','
    print(str1.find(str2))

    print('第{}行，第{}列，分号与其他列数量不一样'.format(4, 7))
