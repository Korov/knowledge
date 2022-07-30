use log::*;

pub fn valid_square(p1: Vec<i32>, p2: Vec<i32>, p3: Vec<i32>, p4: Vec<i32>) -> bool {
    if is_square(&p1, &p2, &p3, &p4) {
        return true;
    }
    if is_square(&p1, &p3, &p2, &p4) {
        return true;
    }
    if is_square(&p1, &p4, &p2, &p3) {
        return true;
    }
    false
}

pub fn is_square(p1: &Vec<i32>, p2: &Vec<i32>, p3: &Vec<i32>, p4: &Vec<i32>) -> bool {
    if p1[0] == p2[0] {
        if p3[0] != p4[0] {
            return false;
        }

        if p1[1] == p3[1] {
            if p2[1] != p4[1] {
                return false;
            }
            let length = { p2[1] - p1[1] }.abs();
            let wide = { p3[0] - p1[0] }.abs();
            if length == wide {
                return true;
            }
        } else {
            if p2[1] != p3[1] {
                return false;
            }
            let length = { p2[1] - p1[1] }.abs();
            let wide = { p4[0] - p1[0] }.abs();
            if length == wide {
                return true;
            }
        }
    }
    false
}

pub fn find_median_sorted_arrays(nums1: Vec<i32>, nums2: Vec<i32>) -> f64 {
    let all_length = nums1.len() + nums2.len();
    let mid = all_length / 2;
    let mut all_nums = Vec::new();
    if all_length % 2 == 1 {
        let mut index1 = 0;
        let mut index2 = 0;
        for _i in 0..mid + 1 {
            if nums1[index1] <= nums2[index2] {
                if index1 < nums1.len() {
                    all_nums.push(nums1[index1]);
                    index1 = index1 + 1;
                } else {
                    all_nums.push(nums2[index2]);
                    index2 = index2 + 1;
                }
            } else {
                if index2 < nums2.len() {
                    all_nums.push(nums2[index2]);
                    index2 = index2 + 1;
                } else {
                    all_nums.push(nums1[index1]);
                    index1 = index1 + 1;
                }
            }
        }
        return f64::from(all_nums[mid]);
    } else {
        let mid2 = mid + 1;
        let mut index1 = 0;
        let mut index2 = 0;
        for i in 0..mid2 {
            if nums1[index1] <= nums2[index2] {
                if index1 < nums1.len() {
                    all_nums.push(nums1[index1]);
                    index1 = index1 + 1;
                } else {
                    all_nums.push(nums2[index2]);
                    index2 = index2 + 1;
                }
            } else {
                if index2 < nums2.len() {
                    all_nums.push(nums2[index2]);
                    index2 = index2 + 1;
                } else {
                    all_nums.push(nums1[index1]);
                    index1 = index1 + 1;
                }
            }
        }
        return f64::from(all_nums[mid - 1] + all_nums[mid2 - 1]) / 2.0_f64;
    }
}

#[cfg(test)]
mod test {
    use env_logger::*;

    use super::*;

    fn init() {
        let mut builder = Builder::from_default_env();
        builder.target(Target::Stdout);
        builder.filter_level(LevelFilter::Info);

        builder.init();
    }

    #[test]
    fn test_test1() {
        init();
        let number_q = 8 % 2;
        info!("number: {}", number_q);
        let number_f = 7.0_f64 / 2.0_f64;
        info!("number: {}", number_f);
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

    #[test]
    fn test_find_median_sorted_arrays() {
        init();
        let nums1 = vec![1, 3];
        let nums2 = vec![2];
        let result = find_median_sorted_arrays(nums1, nums2);
        info!("result:{}", result)
    }
}
