// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/proto/PreparingData.proto

package ru.shadowsparky.screencast.proto;

public final class PreparingDataOuterClass {
  private PreparingDataOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface PreparingDataOrBuilder extends
      // @@protoc_insertion_point(interface_extends:ru.shadowsparky.screencast.proto.PreparingData)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>optional int32 width = 1;</code>
     */
    int getWidth();

    /**
     * <code>optional int32 height = 2;</code>
     */
    int getHeight();

    /**
     * <code>optional string password = 3;</code>
     */
    java.lang.String getPassword();
    /**
     * <code>optional string password = 3;</code>
     */
    com.google.protobuf.ByteString
        getPasswordBytes();
  }
  /**
   * Protobuf type {@code ru.shadowsparky.screencast.proto.PreparingData}
   */
  public  static final class PreparingData extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:ru.shadowsparky.screencast.proto.PreparingData)
      PreparingDataOrBuilder {
    // Use PreparingData.newBuilder() to construct.
    private PreparingData(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private PreparingData() {
      width_ = 0;
      height_ = 0;
      password_ = "";
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private PreparingData(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      int mutable_bitField0_ = 0;
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!input.skipField(tag)) {
                done = true;
              }
              break;
            }
            case 8: {

              width_ = input.readInt32();
              break;
            }
            case 16: {

              height_ = input.readInt32();
              break;
            }
            case 26: {
              java.lang.String s = input.readStringRequireUtf8();

              password_ = s;
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return ru.shadowsparky.screencast.proto.PreparingDataOuterClass.internal_static_ru_shadowsparky_screencast_proto_PreparingData_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ru.shadowsparky.screencast.proto.PreparingDataOuterClass.internal_static_ru_shadowsparky_screencast_proto_PreparingData_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData.class, ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData.Builder.class);
    }

    public static final int WIDTH_FIELD_NUMBER = 1;
    private int width_;
    /**
     * <code>optional int32 width = 1;</code>
     */
    public int getWidth() {
      return width_;
    }

    public static final int HEIGHT_FIELD_NUMBER = 2;
    private int height_;
    /**
     * <code>optional int32 height = 2;</code>
     */
    public int getHeight() {
      return height_;
    }

    public static final int PASSWORD_FIELD_NUMBER = 3;
    private volatile java.lang.Object password_;
    /**
     * <code>optional string password = 3;</code>
     */
    public java.lang.String getPassword() {
      java.lang.Object ref = password_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        password_ = s;
        return s;
      }
    }
    /**
     * <code>optional string password = 3;</code>
     */
    public com.google.protobuf.ByteString
        getPasswordBytes() {
      java.lang.Object ref = password_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        password_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (width_ != 0) {
        output.writeInt32(1, width_);
      }
      if (height_ != 0) {
        output.writeInt32(2, height_);
      }
      if (!getPasswordBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, password_);
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (width_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, width_);
      }
      if (height_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, height_);
      }
      if (!getPasswordBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, password_);
      }
      memoizedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData)) {
        return super.equals(obj);
      }
      ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData other = (ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData) obj;

      boolean result = true;
      result = result && (getWidth()
          == other.getWidth());
      result = result && (getHeight()
          == other.getHeight());
      result = result && getPassword()
          .equals(other.getPassword());
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptorForType().hashCode();
      hash = (37 * hash) + WIDTH_FIELD_NUMBER;
      hash = (53 * hash) + getWidth();
      hash = (37 * hash) + HEIGHT_FIELD_NUMBER;
      hash = (53 * hash) + getHeight();
      hash = (37 * hash) + PASSWORD_FIELD_NUMBER;
      hash = (53 * hash) + getPassword().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code ru.shadowsparky.screencast.proto.PreparingData}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:ru.shadowsparky.screencast.proto.PreparingData)
        ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingDataOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return ru.shadowsparky.screencast.proto.PreparingDataOuterClass.internal_static_ru_shadowsparky_screencast_proto_PreparingData_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return ru.shadowsparky.screencast.proto.PreparingDataOuterClass.internal_static_ru_shadowsparky_screencast_proto_PreparingData_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData.class, ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData.Builder.class);
      }

      // Construct using ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        width_ = 0;

        height_ = 0;

        password_ = "";

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return ru.shadowsparky.screencast.proto.PreparingDataOuterClass.internal_static_ru_shadowsparky_screencast_proto_PreparingData_descriptor;
      }

      public ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData getDefaultInstanceForType() {
        return ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData.getDefaultInstance();
      }

      public ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData build() {
        ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData buildPartial() {
        ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData result = new ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData(this);
        result.width_ = width_;
        result.height_ = height_;
        result.password_ = password_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData) {
          return mergeFrom((ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData other) {
        if (other == ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData.getDefaultInstance()) return this;
        if (other.getWidth() != 0) {
          setWidth(other.getWidth());
        }
        if (other.getHeight() != 0) {
          setHeight(other.getHeight());
        }
        if (!other.getPassword().isEmpty()) {
          password_ = other.password_;
          onChanged();
        }
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int width_ ;
      /**
       * <code>optional int32 width = 1;</code>
       */
      public int getWidth() {
        return width_;
      }
      /**
       * <code>optional int32 width = 1;</code>
       */
      public Builder setWidth(int value) {
        
        width_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 width = 1;</code>
       */
      public Builder clearWidth() {
        
        width_ = 0;
        onChanged();
        return this;
      }

      private int height_ ;
      /**
       * <code>optional int32 height = 2;</code>
       */
      public int getHeight() {
        return height_;
      }
      /**
       * <code>optional int32 height = 2;</code>
       */
      public Builder setHeight(int value) {
        
        height_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 height = 2;</code>
       */
      public Builder clearHeight() {
        
        height_ = 0;
        onChanged();
        return this;
      }

      private java.lang.Object password_ = "";
      /**
       * <code>optional string password = 3;</code>
       */
      public java.lang.String getPassword() {
        java.lang.Object ref = password_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          password_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>optional string password = 3;</code>
       */
      public com.google.protobuf.ByteString
          getPasswordBytes() {
        java.lang.Object ref = password_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          password_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string password = 3;</code>
       */
      public Builder setPassword(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        password_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string password = 3;</code>
       */
      public Builder clearPassword() {
        
        password_ = getDefaultInstance().getPassword();
        onChanged();
        return this;
      }
      /**
       * <code>optional string password = 3;</code>
       */
      public Builder setPasswordBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        password_ = value;
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }


      // @@protoc_insertion_point(builder_scope:ru.shadowsparky.screencast.proto.PreparingData)
    }

    // @@protoc_insertion_point(class_scope:ru.shadowsparky.screencast.proto.PreparingData)
    private static final ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData();
    }

    public static ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<PreparingData>
        PARSER = new com.google.protobuf.AbstractParser<PreparingData>() {
      public PreparingData parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new PreparingData(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<PreparingData> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<PreparingData> getParserForType() {
      return PARSER;
    }

    public ru.shadowsparky.screencast.proto.PreparingDataOuterClass.PreparingData getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ru_shadowsparky_screencast_proto_PreparingData_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ru_shadowsparky_screencast_proto_PreparingData_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\"src/main/proto/PreparingData.proto\022 ru" +
      ".shadowsparky.screencast.proto\"@\n\rPrepar" +
      "ingData\022\r\n\005width\030\001 \001(\005\022\016\n\006height\030\002 \001(\005\022\020" +
      "\n\010password\030\003 \001(\tb\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_ru_shadowsparky_screencast_proto_PreparingData_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ru_shadowsparky_screencast_proto_PreparingData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ru_shadowsparky_screencast_proto_PreparingData_descriptor,
        new java.lang.String[] { "Width", "Height", "Password", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
