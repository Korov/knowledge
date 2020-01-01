def remove_line_breaks(oldString):
    return oldString.replace('\n', '').replace('\r', '')


if __name__ == "__main__":
    newString = remove_line_breaks('''而非1;
而非2;
而非3''')
    print(newString)
