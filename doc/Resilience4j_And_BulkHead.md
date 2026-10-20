1\.	Vấn đề \& Nguyên lý Bulkhead

2\.	Hệ sinh thái \& 2 loại Bulkhead trong Resilience4j

3\.	Phân biệt Pattern \& Nguyên lý thiết kế hệ thống (so sánh kỹ thuật: Bulkhead khác gì với Circuit Breaker, Rate Limiter, Time Limiter, và Retry)

4\.	Vai trò, Ưu điểm, Hạn chế \& Kinh nghiệm cấu hình



Thuyết trình Bulkhead và Resilience4J:



KHÁI NIỆM:

\- Resilience4J là một thư viện(khả năng chịu lỗi), cung cấp các module xử lý sự cố trong microservice: circuit breaker, rate limiter, retry, timelimiter và bulkhead



\- Bulkheade pattern phân chia tài nguyên hệ thống thành các phần độc lập cho từng dịch vụ hoặc chức năng

&#x20;+ Mục tiêu chính: là rào chắn lỗi(Fault Isolation) ngăn chặn hiện tượng sự số dây chuyền(cascading failure)

&#x20;+ Bài toán thực tế: ví dụ khi một Service gọi tới 3 API(A, B, C) bên ngoài. Nếu API A bị treo hoặc chậm, các request tới API-A sẽ chiếm toàn bộ luồng của hệ thống dẫn tới việc API-B và C dù hoạt động bình thường vẫn sẽ bị từ chối vì không còn luồng đi trong hệ thống. Bulkhead khắc phục bằng cách giới hạn số luồng tối đa cho API-C





TRIỂN KHAI:

Resilience4j triển khai Bulkhead dưới 2 dạng chính:



1\. SemaphoreBulkhead (Mặc định) (Note câu hỏi tìm hiểu: Mặc định nghĩa là bản thân nó đã có sẵn hay vẫn phải triển khai trong code?)

\- Cơ chế: Sử dụng java.util.concurrent.Semaphore để giới hạn số lượng request chạy đồng thời (concurrent requests)

\- Đặc điểm:

&#x20;+ Không chuyển đổi context giữa các Thread (Thread context switch minimal)

&#x20;+ Chạy trực tiếp trên caller thread

&#x20;+ Hiệu năng cao, overhead rất thấp

&#x20;+ Không hỗ trợ hàng đợi (Queue)



2\. ThreadPoolBulkhead

\- Cơ chế: Sử dụng một ThreadPoolExecutor riêng biệt kèm theo một hàng đợi (ArrayBlockingQueue)

\- Đặc điểm:

&#x20;+ Bổ sung khả năng cách ly Thread hoàn toàn (Thread Isolation)

&#x20;+ Request sẽ được đẩy vào thread pool riêng của Bulkhead

&#x20;+ Hỗ trợ gán kích thước hàng đợi (queueCapacity) khi thread pool bị đầy

&#x20;+ Phù hợp cho các tác vụ Blocking I/O hoặc gọi các API bên ngoài mất nhiều thời gian



|Tiêu chí|SemaphoreBulkhead|ThreadPoolBulkhead|
|-|-|-|
|Cơ chế cách ly|Đếm số request đồng thời bằng Semaphore|Dùng Thread Pool và Queue riêng biệt|
|Thread Context|Chạy trên Thread của Caller|Chạy trên Thread của Thread Pool|
|Hỗ trợ Queue|Không|Có(queueCapacity)|
|Trường hợp sử dụng|Tác vụ xử lý nhanh, non-blocking|Tác vụ blooking I/O, gọi API bên ngoài lâu|





CẤU HÌNH:

1. File pom.xml

<dependency>

&#x20;   <groupId>io.github.resilience4j</groupId>

&#x20;   <artifactId>resilience4j-spring-boot3</artifactId>

&#x20;   <version>2.2.0</version>

</dependency>

<dependency>

&#x20;   <groupId>org.springframework.boot</groupId>

&#x20;   <artifactId>spring-boot-starter-aop</artifactId>

</dependency>



2\. application.yml

resilience4j:

&#x20; bulkhead:

&#x20;   instances:

&#x20;     paymentServiceBulkhead:

&#x20;       maxConcurrentCalls: 10          # Tối đa 10 request đồng thời

&#x20;       maxWaitDuration: 20mms          # Thời gian chờ tối đa nếu bulkhead đã đầy



&#x20; thread-pool-bulkhead:

&#x20;   instances:

&#x20;     reportServiceBulkhead:

&#x20;       maxThreadPoolSize: 5            # Số thread tối đa trong pool

&#x20;       coreThreadPoolSize: 2           # Số thread khởi tạo

&#x20;       queueCapacity: 10               # Sức chứa hàng đợi

&#x20;       keepAliveDuration: 20ms         # Thời gian sống của thread dôi ra



NGUYÊN TẮC áp dụng:

1. Kết hợp các Pattern: Để tối ưu hóa độ tin cậy, Bulkhead thường được sử dụng kết hợp với các component khác của Resilience4j theo thứ tự bọc (Decorator order):
Retry --> CircuitBreaker --> RateLimiter --> Bulkhead --> Target Method
2. Xác định giới hạn phù hợp: Cần benchmark / load test hệ thống để tìm số lượng maxConcurrentCalls hoặc maxThreadPoolSize hợp lý, tránh đặt quá lớn làm mất tác dụng bảo vệ hoặc quá nhỏ làm từ chối request hợp lệ.
3. Luôn có giải pháp Fallback: Đảm bảo triển khai fallbackMethod để trả về phản hồi hợp lý cho người dùng thay vì quăng lỗi crash ứng dụng khi gặp BulkheadFullException.






**Các trường hợp demo:** 

1. **Semaphore Bulkhead (Cô lập số lượng request xử lý đồng thời)**

\- Ý tưởng: Giới hạn tối đa $N$ request Parse CV được xử lý cùng một thời điểm (ví dụ: tối đa 5 requests).

\- Ứng dụng: Giới hạn trực tiếp tại Feign Client hoặc Service layer gọi sang OCR/AI Parser Engine. Các request thứ 6 trở đi khi vượt ngưỡng sẽ lập tức bị từ chối hoặc bắt chờ trong một khoảng thời gian ngắn (maxWaitDuration), giúp giữ tài nguyên cho các tính năng khác.

**2. ThreadPool Bulkhead (Cô lập tài nguyên Thread bất đồng bộ)**

\- Ý tưởng: Cấp riêng một Thread Pool độc lập (ví dụ: 5 threads) chỉ dành riêng cho tác vụ gọi Parse CV.

\- Ứng dụng: Request từ ứng viên gửi đến Tomcat Thread Pool của candidate-service sẽ ngay lập tức được đẩy vào Queue của ThreadPool Bulkhead rồi trả về phản hồi sơ bộ (hoặc xử lý CompletableFuture). Tomcat Thread chính được giải phóng ngay lập tức.

**3. Phối hợp chuỗi Resilience Pattern (Bulkhead + TimeLimiter + CircuitBreaker + Message Queue Fallback)**

\- Ý tưởng: Kết hợp Bulkhead (giới hạn sức chứa) + TimeLimiter (ngắt kết nối nếu parse lâu hơn 10s) + Circuit Breaker (ngắt mạch khi dịch vụ parse CV lỗi liên tục) + Asynchronous Fallback Queue.

\- Ứng dụng: Nếu Bulkhead đầy hoặc Parse CV bị timeout, hệ thống tự động lưu file CV với trạng thái ParseStatus.PENDING và đẩy một Event vào Message Queue (RabbitMQ/Kafka) để worker ngầm xử lý sau. Ứng viên vẫn nộp CV thành công mà không bị báo lỗi 500.

