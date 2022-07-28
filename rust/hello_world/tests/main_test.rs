extern crate hello_world_lib;

#[test]
fn it_adds_two() {
    assert_eq!(4, hello_world_lib::child_mod::add_two(2));
}

#[test]
fn it_adds_two1() {
    assert_eq!(4, hello_world_lib::add_two1(2));
}
