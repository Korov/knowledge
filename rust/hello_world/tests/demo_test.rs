pub fn add(a: i32, b: i32) -> i32 {
    a + b
}

pub fn print_test() {
    let s1 = String::from("hello");
    let s2 = s1;

    println!("{}, world!", s2);
}

#[cfg(test)]
mod test {
    use crate::*;

    #[test]
    fn add_test() {
        assert_eq!(4 + 2, add(4, 2))
    }

    #[test]
    fn print_test_test() {
        print_test()
    }
}
