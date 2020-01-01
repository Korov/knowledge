package com.korov.cloud.rolemanager.provider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MongoUserEntity {
    @Id
    private String id;
    private String userId;
    private String fileName;
}
