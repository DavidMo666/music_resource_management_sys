# Music Resource Management System (Backend) - Feature Introduction

![749e37b312a626e3f57cba9575ebab09](https://github.com/user-attachments/assets/77755797-a6e1-4b7e-bbd6-c7a988f354ab)

## 1. Fine-Grained Role-Based Permission Control for Secure Resource Management  
The system strictly differentiates between admin and user roles to achieve permission isolation:  
- Admins have full lifecycle management permissions for music resources, including pagination queries, batch deletion, status control (blocking/unblocking), and multi-condition retrieval, ensuring resource compliance through status management.  
- Regular users can only manage resources they uploaded (add, delete), ensuring clear resource ownership and well-defined operational boundaries.  


## 2. Efficient Data Processing and Flexible Query Capabilities  
- **Intelligent Pagination & Multi-Condition Filtering**: Leverages PageHelper for pagination queries, supporting multi-dimensional filtering by resource type, status, upload time, etc., combined with custom sorting (ascending/descending) for quick targeting of resources.  
- **Rigorous Parameter Validation**: Validates input legitimacy for all interfaces (e.g., checking ID formats during batch deletion, restricting valid values for status updates), preventing invalid data from entering the system and enhancing stability.  


## 3. Robust Tech Stack Ensuring Performance and Scalability  
- Built on Spring Boot, integrated with MyBatis for efficient data access, and paired with Redis for caching temporary data (e.g., verification codes) to reduce database load.  
- Adopts JWT for stateless identity authentication to secure API access; integrates Knife4j for automatic API documentation generation, simplifying front-end and back-end collaboration.  
- Uses Lombok to streamline code and follows MVC layered design, facilitating future function iterations and maintenance.  


## 4. Full-Lifecycle Resource Management Covering Core Scenarios  
Supports the complete lifecycle of music resources from upload to deletion:  
- Automatically records metadata (time, user ownership) upon upload, with a default status of "normal".  
- Admins can batch clean up invalid resources and manage non-compliant content, while users can independently maintain their own resources, meeting resource governance needs across different scenarios.

## The following pictures show some of the main pages：
<p align="center">
  <img src="https://github.com/user-attachments/assets/ec0af464-a573-4ff3-9172-41d6a08fcac7" alt="b8cf4efaa0dc8aa58d56b9669a47df73">
</p>
<p align="center">
  <img src="https://github.com/user-attachments/assets/49e7c42d-cdb9-4c34-b52d-09c94e888fbc" alt="c266a68fe96228925b832164c650e414">
</p>

## 5. Optimization (⚠️ Important !!!)

We have now optimized this project and integrated multiple core functions and technologies, mainly including:

1. Enterprise-Grade Security and Permission Control  
   - Designed and implemented a role-based secure access system using Spring Security and JWT authentication mechanisms, supporting dynamic RBAC (Role-Based Access Control) authorization policies to meet the permission isolation requirements of users at different levels;  
   - Adopted the Bcrypt encryption algorithm to encrypt and store sensitive user information, ensuring the security and compliance of system access.  

2. RESTful-Style Resource Management Services  
   - Developed RESTful APIs to support MP3 audio file uploads, OSS cloud storage implementation, and full lifecycle management of music metadata (name, type, uploader, etc.);  
   - Integrated Jenkins to achieve CI/CD automated deployment, and paired with Swagger for auto-generated API documentation to improve the maintainability and collaboration efficiency of interfaces.  

3. High-Performance Retrieval and Audio/Video Processing  
   - Integrated Elasticsearch search engine to support multi-condition (name, type, uploader, time, etc.) precise retrieval of music resources, improving retrieval efficiency;  
   - Integrated the FFmpeg tool to implement adaptive streaming transcoding, adapting to audio playback requirements of different terminals and optimizing user playback experience.  

4. High-Concurrency Optimization and Performance Validation  
   - Cached user session information and music resource data based on Redis, reducing database access pressure in high-concurrency scenarios and lowering interface response latency;  
   - Conducted system scalability and stability benchmarking using JMeter, simulating access scenarios with 1000+ concurrent users to verify the system's reliability under high concurrency.

This project is expected to be officially launched in April 2026. Currently, it is in the stage of internal development and testing. Therefore, only the basic version will be open-sourced for now, while the fully optimized version will not be open-sourced externally. But we promise to make the optimized version open-source on the day of its launch. The repository for the optimized version is: https://github.com/Y4ng22/SoundSource
