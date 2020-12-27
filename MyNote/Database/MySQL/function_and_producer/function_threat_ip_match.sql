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