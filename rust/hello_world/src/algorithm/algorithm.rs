use log::*;

pub fn test11() {
    print!("hahaha")
}

pub fn valid_square(p1: Vec<i32>, p2: Vec<i32>, p3: Vec<i32>, p4: Vec<i32>) -> bool {
    info!("p1:{:?}, p2:{:?}, p3:{:?}, p4{:?}", p1, p2, p3, p4);
    false
}

#[cfg(test)]
mod test {
    use super::*;
    use env_logger::*;

    fn init() {
        let mut builder = Builder::from_default_env();
        builder.target(Target::Stdout);
        builder.filter_level(LevelFilter::Info);

        builder.init();
    }

    #[test]
    fn test_test() {
        test11();
    }

    #[test]
    fn test_valid_square() {
        init();
        let p1 = vec![0, 0];

        let p2 = vec![1, 1];

        let p3 = vec![1, 0];

        let p4 = vec![0, 1];
        valid_square(p1, p2, p3, p4);
    }
}
