from smb.SMBConnection import SMBConnection


def get_script_file():
    userName = 'root'
    userPassword = 'root123'
    hostIp = '192.168.106.142'
    hostPort = 445
    try:
        samba = SMBConnection(userName, userPassword, '', '', use_ntlm_v2=True)
        # 返回值为布尔型，表示连接成功与否
        result = samba.connect(hostIp, hostPort)
        status = samba.auth_result
        if status:
            print('连接成功')
        else:
            print("连接失败")
    except:
        print("异常")
        samba.close()


def printMyString(value):
    print('debug')
    print(value)


if __name__ == '__main__':
    # printMyString('hello world')
    get_script_file()
