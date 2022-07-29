use log::*;

pub fn test11() {
    print!("hahaha")
}

pub fn valid_square(p1: Vec<i32>, p2: Vec<i32>, p3: Vec<i32>, p4: Vec<i32>) -> bool {
    if is_square(&p1, &p2, &p3, &p4) {
        return true
    }
    if is_square(&p1, &p3, &p2, &p4) {
        return true
    }
    if is_square(&p1, &p4, &p2, &p3) {
        return true
    }
    false
}

pub fn is_square(p1: &Vec<i32>, p2: &Vec<i32>, p3: &Vec<i32>, p4: &Vec<i32>) -> bool {
    if p1[0] == p2[0] {
        if p3[0] != p4[0] {
            return false
        }
        
        if p1[1] == p3[1] {
            if p2[1] != p4[1] {
                return false
            }
            let length = {p2[1] - p1[1]}.abs();
            let wide = {p3[0] - p1[0]}.abs();
            if length == wide {
                return true
            }
        } else {
            if p2[1] != p3[1] {
                return false
            }
            let length = {p2[1] - p1[1]}.abs();
            let wide = {p4[0] - p1[0]}.abs();
            if length == wide {
                return true
            }
        }
    }
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
        let result = valid_square(p1, p2, p3, p4);
        info!("result:{}", result)
    }
}
