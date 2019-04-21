all: generate_proto

PROTO_COMPILER=protoc
OUT_PATH=--java_out=./src/main/java/
PROTO_PATH=src/main/proto/

generate_proto: ; $(PROTO_COMPILER) $(OUT_PATH) $(PROTO_PATH)*.proto
