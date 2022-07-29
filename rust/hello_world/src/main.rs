pub mod algorithm;

use env_logger::{Builder, Target};
use log::{debug, error, info, log_enabled, Level, LevelFilter};

fn main() {
    // 注意，env_logger 必须尽可能早的初始化
    // env_logger::init();
    // log::set_max_level(LevelFilter::Info);
    let mut builder = Builder::from_default_env();
    builder.target(Target::Stdout);
    builder.filter_level(LevelFilter::Info);

    builder.init();

    debug!("this is a debug {}", "message");
    error!("this is printed by default");
    info!("this is a info log");

    if log_enabled!(Level::Info) {
        let x = 3 * 4; // expensive computation
        info!("the answer was: {}", x);
    }
}
