package com.bleuon.service.impl;

import com.bleuon.mapper.DiscussionMapper;
import com.bleuon.service.IDiscussionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @package: com.bleuon.service.impl
 * @author: zheng
 * @date: 2023/10/14
 */
@Service
@RequiredArgsConstructor
public class DiscussionService implements IDiscussionService {

    private final DiscussionMapper discussionMapper;

}
