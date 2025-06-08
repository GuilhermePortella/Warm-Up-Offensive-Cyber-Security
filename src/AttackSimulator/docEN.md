# AttackSimulator

## 🔧 Overview
A modular and reusable Java application designed to simulate various HTTP traffic patterns on web servers. Focused on load testing, resilience evaluation, and slow connection behavior analysis. Perfect for studying networks, DevOps, and offensive security in controlled environments.

## 📊 Key Features
- Multi-host/IP simultaneous testing
- Multi-threaded execution using ExecutorService
- Dual operation modes: normal and slowloris
- Host-specific logging system
- Per-IP bandwidth consumption tracking
- Reusable template for network testing

## 🗂️ Project Structure
```
AttackSimulator/
├── AttackSimulator.java   # Main CLI class
├── AttackTask.java        # Per-host attack thread
├── LoggerUtil.java        # File logging utility
└── BandwidthTracker.java  # IP traffic measurement
```

## 🚀 Requirements
- Java 8+
- TCP socket-compatible OS (Windows/Linux/Mac)
- Proper TCP outbound connection permissions

## ⚙️ Usage

### Basic Command
```bash
java AttackSimulator <hosts> <port> <threads> <mode>
```

### Parameters
| Parameter | Type    | Description                        |
|-----------|---------|-----------------------------------|
| hosts     | String  | Comma-separated IPs/domains       |
| port      | Integer | Target TCP port                   |
| threads   | Integer | Thread count per host             |
| mode      | String  | `normal` or `slowloris`          |

### Examples
1. Normal attack on localhost:8080 with 10 threads:
```bash
java AttackSimulator localhost 8080 10 normal
```

2. Normal attack on multiple IPs:
```bash
java AttackSimulator localhost,192.168.0.50 8080 10 normal
```

3. Slowloris simulation on multiple IPs:
```bash
java AttackSimulator 127.0.0.1,192.168.0.105 80 10 slowloris
```

## 📁 Logging
Logs are created as separate files per host:
```
log_127_0_0_1.txt
log_192_168_0_105.txt
```

Sample log entry:
```
[2025-06-06 17:45:10] [NORMAL][127.0.0.1] Time: 34ms, Bytes received: 245
[2025-06-06 17:45:11] [SLOWLORIS] Fragment sent.
...
TOTAL Sent: 3287 bytes, Received: 5892 bytes
```

## 🔄 Modularity & Reusability
- **AttackTask**: Extensible for other attack patterns (HTTP Flood, Smuggling)
- **LoggerUtil**: Universal Java logging solution
- **BandwidthTracker**: Reusable for web/desktop traffic monitoring

## ⚖️ Legal Disclaimer
> ⚠️ **Educational purpose only**. This project is strictly for controlled environment testing.
> Never attempt attacks on systems without explicit authorization.

## 💡 Future Enhancements
- [ ] Proxy attack support
- [ ] Malicious header simulation
- [ ] Traffic visualization dashboard

## 🤝 Contributing
Contributions via pull requests and suggestions are welcome!

## 📄 License
MIT