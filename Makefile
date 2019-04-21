all: generate_proto

generate_proto: ; protoc --java_out=./src/main/java/  src/main/proto/*.proto
