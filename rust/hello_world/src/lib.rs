pub fn add_two1(a: i32) -> i32 {
    a + 2
}

pub mod child_mod {
    pub fn add_two(a: i32) -> i32 {
        a + 2
    }
}

#[cfg(test)]
mod tests {
    use super::{add_two1, child_mod::add_two};

    

    #[test]
    fn it_works() {
        assert_eq!(add_two(2), 4)
    }

    #[test]
    fn it_works1() {
        assert_eq!(add_two1(2), 4)
    }

    pub fn greeting(name: &str) -> String {
        format!("Hello {}!", name)
    }
    #[test]
    fn it_works2() {
        let result = greeting("Sunface");
        let target = "孙飞";
        assert!(
            result.contains(target),
            "你的问候中并没有包含目标姓名 {} ，你的问候是 `{}`",
            target,
            result
        );
    }
}
