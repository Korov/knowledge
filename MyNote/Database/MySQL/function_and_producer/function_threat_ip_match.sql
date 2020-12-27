delimiter $$
DROP function if exists match_threat_ip;
CREATE function match_threat_ip(delimiter_value varchar(5), ip1 text, ip2 text) returns boolean
    Deterministic
begin
    DECLARE ip_count1 INT default 0;
    DECLARE ip_count2 INT default 0;
    DECLARE join_count INT default 0;

    if (delimiter_value is null or ip1 is null or ip2 is null) then
        return false;
    end if;

    set ip_count1 = length(ip1) - length(replace(ip1, delimiter_value, '')) + 1;
    set ip_count2 = length(ip2) - length(replace(ip2, delimiter_value, '')) + 1;

    if (ip_count1 != ip_count2) THEN
        return false;
    else
        select count(*)
        into join_count
        from (SELECT substring_index(substring_index(substring_index(a.ip, delimiter_value, b.help_topic_id + 1),
                                                     delimiter_value, - 1), ':', 1) AS ips
              FROM (select ip1 as ip) a
                       JOIN mysql.help_topic b ON b.help_topic_id <
                                                  (length(a.ip) - length(replace(a.ip, delimiter_value, '')) + 1)) ips1
                 join (SELECT substring_index(
                                      substring_index(substring_index(a.ip, delimiter_value, b.help_topic_id + 1),
                                                      delimiter_value, - 1), ':', 1) AS ips
                       FROM (select ip2 as ip) a
                                JOIN mysql.help_topic b ON b.help_topic_id <
                                                           (length(a.ip) - length(replace(a.ip, delimiter_value, '')) + 1)) ips2
                      on ips1.ips = ips2.ips;
        if join_count = ip_count1 then
            return true;
        else
            return false;
        end if;
    end if;
end
$$
delimiter ;


delimiter $$
DROP function if exists match_threat_user_name;
CREATE function match_threat_user_name(port1 text, port2 text) returns boolean
    Deterministic
begin
    DECLARE port_count1 INT default 0;
    DECLARE port_count2 INT default 0;
    DECLARE join_count INT default 0;

    if (port1 is null or port2 is null) then
        return false;
    end if;

    set port_count1 = length(port1) - length(replace(port1, ':', '')) + 1;
    set port_count2 = length(port2) - length(replace(port2, ':', '')) + 1;

    if (port_count1 != port_count2) THEN
        return false;
    else
        select count(*)
        into join_count
        from (SELECT substring_index(a.port, '(?<=:)\\d+(?=,)|(?<=:)\\d+$', 1, b.help_topic_id + 1) AS ports
              FROM (select port1 as port) a
                       JOIN mysql.help_topic b ON b.help_topic_id < port_count1) ports1
                 join (SELECT substring_index(a.port, '(?<=:)\\d+(?=,)|(?<=:)\\d+$', 1, b.help_topic_id + 1) AS ports
                       FROM (select port2 as port) a
                                JOIN mysql.help_topic b ON b.help_topic_id < port_count2) ports2
                      on ports1.ports = ports2.ports;
        if join_count = port_count1 then
            return true;
        else
            return false;
        end if;
    end if;
end
$$
delimiter ;


delimiter $$
DROP function if exists match_threat_user_name;
CREATE function match_threat_user_name(delimiter_value varchar(5), user_name1 text, user_name2 text) returns boolean
    Deterministic
begin
    DECLARE user_name_count1 INT default 0;
    DECLARE user_name_count2 INT default 0;
    DECLARE join_count INT default 0;

    if (delimiter_value is null or user_name1 is null or user_name2 is null) then
        return false;
    end if;

    set user_name_count1 = length(user_name1) - length(replace(user_name1, delimiter_value, '')) + 1;
    set user_name_count2 = length(user_name2) - length(replace(user_name2, delimiter_value, '')) + 1;

    if (user_name_count1 != user_name_count2) THEN
        return false;
    else
        select count(*)
        into join_count
        from (SELECT substring_index(substring_index(a.user_name, delimiter_value, b.help_topic_id + 1),
                                     delimiter_value, - 1) AS user_names
              FROM (select user_name1 as user_name) a
                       JOIN mysql.help_topic b ON b.help_topic_id < (length(a.user_name) -
                                                                     length(replace(a.user_name, delimiter_value, '')) +
                                                                     1)) ips1
                 join (SELECT substring_index(substring_index(a.user_name, delimiter_value, b.help_topic_id + 1),
                                              delimiter_value, - 1) AS user_names
                       FROM (select user_name2 as user_name) a
                                JOIN mysql.help_topic b ON b.help_topic_id < (length(a.user_name) -
                                                                              length(replace(a.user_name, delimiter_value, '')) +
                                                                              1)) ips2
                      on ips1.user_names = ips2.user_names;
        if join_count = user_name_count1 then
            return true;
        else
            return false;
        end if;
    end if;
end
$$
delimiter ;