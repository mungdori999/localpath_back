#!/usr/bin/env bash
# EC2 최초 1회 실행 예시 (Ubuntu)
set -euo pipefail

APP_DIR="${APP_DIR:-/home/ubuntu/localpath}"

sudo mkdir -p "$APP_DIR"
sudo chown "$USER:$USER" "$APP_DIR"

# Java 21 (Ubuntu)
sudo apt-get update
sudo apt-get install -y openjdk-21-jdk

# systemd 서비스 등록 (경로·User는 환경에 맞게 수정)
sudo cp scripts/ec2/localpath.service /etc/systemd/system/localpath.service
sudo systemctl daemon-reload
sudo systemctl enable localpath

echo "Done. Set JWT_SECRET_KEY in /etc/systemd/system/localpath.service then:"
echo "  sudo systemctl start localpath"
