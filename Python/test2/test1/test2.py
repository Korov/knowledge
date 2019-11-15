# -*- coding:utf-8 -*-

import os
import sys

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
            return samba
        else:
            print("连接失败")
    except:
        print("异常")
        samba.close()

def copyFromSharePath(srcFilename, desFilename):
    samba = get_script_file()

    localFile = open(desFilename, "wb")

    # samba.storeFile(srcFilename, desFilename, localFile)
    samba.retrieveFile('public', '/test1.txt', localFile)


def main(argv=sys.argv):
    if not len(argv) == 3:
        print('input parameters\'s count should be 3,not %s' % (len(argv)))
        return
    print(u'脚本名字是：' + argv[0])
    srcFilename = argv[1]
    print(u'源目录：' + argv[1])
    desFilename = argv[2]
    print(u'目标目录：' + argv[2])
    copyFromSharePath(srcFilename, desFilename)


if __name__ == '__main__':
    hostIp = '192.168.106.142'
    sharePath = '\\public'
    filename = 'test2.txt'

    resultStr = []
    resultStr.append([])

    srcFilename = '\\\\' + hostIp + sharePath + '\\' + filename

    desFilename = 'F:\\Download\\temp\\test.txt'
    cmd = [
        'D:\\python1\\test3.py',
        srcFilename,
        desFilename
    ]
    main(cmd)
    print('ok')
