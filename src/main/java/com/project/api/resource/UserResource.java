package com.project.api.resource;

import com.project.api.model.JobLaunchRequest;
import com.project.api.service.UserService;
import com.project.api.service.dto.UserDTO;
import com.project.api.service.dto.UserIDTO;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobExplorer jobExplorer;
    @Autowired
    private ApplicationContext context;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<UserDTO> insert(@RequestBody UserIDTO userIDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.insert(userIDTO));
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable String id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(id, userDTO));
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value="/import")
    public ExitStatus runJob(@RequestBody JobLaunchRequest request, @RequestParam MultipartFile file) throws Exception {
        Job job = this.context.getBean("jobUserRestService", Job.class);
        JobParameters jobParameters = new JobParametersBuilder(request.getJobParameters(), jobExplorer)
                .getNextJobParameters(job)
                .toJobParameters();

        return this.jobLauncher.run(job, jobParameters).getExitStatus();
    }
}