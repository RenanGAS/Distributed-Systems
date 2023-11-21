// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Movie.proto

package project.javaOut;

/**
 * Protobuf type {@code MovieList}
 */
public final class MovieList extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:MovieList)
    MovieListOrBuilder {
private static final long serialVersionUID = 0L;
  // Use MovieList.newBuilder() to construct.
  private MovieList(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private MovieList() {
    movies_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new MovieList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private MovieList(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              movies_ = new java.util.ArrayList<project.javaOut.Movie>();
              mutable_bitField0_ |= 0x00000001;
            }
            movies_.add(
                input.readMessage(project.javaOut.Movie.parser(), extensionRegistry));
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
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
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        movies_ = java.util.Collections.unmodifiableList(movies_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return project.javaOut.CRUDMovie.internal_static_MovieList_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return project.javaOut.CRUDMovie.internal_static_MovieList_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            project.javaOut.MovieList.class, project.javaOut.MovieList.Builder.class);
  }

  public static final int MOVIES_FIELD_NUMBER = 1;
  private java.util.List<project.javaOut.Movie> movies_;
  /**
   * <code>repeated .Movie movies = 1;</code>
   */
  @java.lang.Override
  public java.util.List<project.javaOut.Movie> getMoviesList() {
    return movies_;
  }
  /**
   * <code>repeated .Movie movies = 1;</code>
   */
  @java.lang.Override
  public java.util.List<? extends project.javaOut.MovieOrBuilder> 
      getMoviesOrBuilderList() {
    return movies_;
  }
  /**
   * <code>repeated .Movie movies = 1;</code>
   */
  @java.lang.Override
  public int getMoviesCount() {
    return movies_.size();
  }
  /**
   * <code>repeated .Movie movies = 1;</code>
   */
  @java.lang.Override
  public project.javaOut.Movie getMovies(int index) {
    return movies_.get(index);
  }
  /**
   * <code>repeated .Movie movies = 1;</code>
   */
  @java.lang.Override
  public project.javaOut.MovieOrBuilder getMoviesOrBuilder(
      int index) {
    return movies_.get(index);
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    for (int i = 0; i < movies_.size(); i++) {
      output.writeMessage(1, movies_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < movies_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, movies_.get(i));
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof project.javaOut.MovieList)) {
      return super.equals(obj);
    }
    project.javaOut.MovieList other = (project.javaOut.MovieList) obj;

    if (!getMoviesList()
        .equals(other.getMoviesList())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (getMoviesCount() > 0) {
      hash = (37 * hash) + MOVIES_FIELD_NUMBER;
      hash = (53 * hash) + getMoviesList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static project.javaOut.MovieList parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static project.javaOut.MovieList parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static project.javaOut.MovieList parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static project.javaOut.MovieList parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static project.javaOut.MovieList parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static project.javaOut.MovieList parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static project.javaOut.MovieList parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static project.javaOut.MovieList parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static project.javaOut.MovieList parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static project.javaOut.MovieList parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static project.javaOut.MovieList parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static project.javaOut.MovieList parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(project.javaOut.MovieList prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
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
   * Protobuf type {@code MovieList}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:MovieList)
      project.javaOut.MovieListOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return project.javaOut.CRUDMovie.internal_static_MovieList_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return project.javaOut.CRUDMovie.internal_static_MovieList_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              project.javaOut.MovieList.class, project.javaOut.MovieList.Builder.class);
    }

    // Construct using project.javaOut.MovieList.newBuilder()
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
        getMoviesFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (moviesBuilder_ == null) {
        movies_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        moviesBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return project.javaOut.CRUDMovie.internal_static_MovieList_descriptor;
    }

    @java.lang.Override
    public project.javaOut.MovieList getDefaultInstanceForType() {
      return project.javaOut.MovieList.getDefaultInstance();
    }

    @java.lang.Override
    public project.javaOut.MovieList build() {
      project.javaOut.MovieList result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public project.javaOut.MovieList buildPartial() {
      project.javaOut.MovieList result = new project.javaOut.MovieList(this);
      int from_bitField0_ = bitField0_;
      if (moviesBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          movies_ = java.util.Collections.unmodifiableList(movies_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.movies_ = movies_;
      } else {
        result.movies_ = moviesBuilder_.build();
      }
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof project.javaOut.MovieList) {
        return mergeFrom((project.javaOut.MovieList)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(project.javaOut.MovieList other) {
      if (other == project.javaOut.MovieList.getDefaultInstance()) return this;
      if (moviesBuilder_ == null) {
        if (!other.movies_.isEmpty()) {
          if (movies_.isEmpty()) {
            movies_ = other.movies_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureMoviesIsMutable();
            movies_.addAll(other.movies_);
          }
          onChanged();
        }
      } else {
        if (!other.movies_.isEmpty()) {
          if (moviesBuilder_.isEmpty()) {
            moviesBuilder_.dispose();
            moviesBuilder_ = null;
            movies_ = other.movies_;
            bitField0_ = (bitField0_ & ~0x00000001);
            moviesBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getMoviesFieldBuilder() : null;
          } else {
            moviesBuilder_.addAllMessages(other.movies_);
          }
        }
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      project.javaOut.MovieList parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (project.javaOut.MovieList) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<project.javaOut.Movie> movies_ =
      java.util.Collections.emptyList();
    private void ensureMoviesIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        movies_ = new java.util.ArrayList<project.javaOut.Movie>(movies_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        project.javaOut.Movie, project.javaOut.Movie.Builder, project.javaOut.MovieOrBuilder> moviesBuilder_;

    /**
     * <code>repeated .Movie movies = 1;</code>
     */
    public java.util.List<project.javaOut.Movie> getMoviesList() {
      if (moviesBuilder_ == null) {
        return java.util.Collections.unmodifiableList(movies_);
      } else {
        return moviesBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .Movie movies = 1;</code>
     */
    public int getMoviesCount() {
      if (moviesBuilder_ == null) {
        return movies_.size();
      } else {
        return moviesBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .Movie movies = 1;</code>
     */
    public project.javaOut.Movie getMovies(int index) {
      if (moviesBuilder_ == null) {
        return movies_.get(index);
      } else {
        return moviesBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .Movie movies = 1;</code>
     */
    public Builder setMovies(
        int index, project.javaOut.Movie value) {
      if (moviesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureMoviesIsMutable();
        movies_.set(index, value);
        onChanged();
      } else {
        moviesBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .Movie movies = 1;</code>
     */
    public Builder setMovies(
        int index, project.javaOut.Movie.Builder builderForValue) {
      if (moviesBuilder_ == null) {
        ensureMoviesIsMutable();
        movies_.set(index, builderForValue.build());
        onChanged();
      } else {
        moviesBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .Movie movies = 1;</code>
     */
    public Builder addMovies(project.javaOut.Movie value) {
      if (moviesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureMoviesIsMutable();
        movies_.add(value);
        onChanged();
      } else {
        moviesBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .Movie movies = 1;</code>
     */
    public Builder addMovies(
        int index, project.javaOut.Movie value) {
      if (moviesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureMoviesIsMutable();
        movies_.add(index, value);
        onChanged();
      } else {
        moviesBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .Movie movies = 1;</code>
     */
    public Builder addMovies(
        project.javaOut.Movie.Builder builderForValue) {
      if (moviesBuilder_ == null) {
        ensureMoviesIsMutable();
        movies_.add(builderForValue.build());
        onChanged();
      } else {
        moviesBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .Movie movies = 1;</code>
     */
    public Builder addMovies(
        int index, project.javaOut.Movie.Builder builderForValue) {
      if (moviesBuilder_ == null) {
        ensureMoviesIsMutable();
        movies_.add(index, builderForValue.build());
        onChanged();
      } else {
        moviesBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .Movie movies = 1;</code>
     */
    public Builder addAllMovies(
        java.lang.Iterable<? extends project.javaOut.Movie> values) {
      if (moviesBuilder_ == null) {
        ensureMoviesIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, movies_);
        onChanged();
      } else {
        moviesBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .Movie movies = 1;</code>
     */
    public Builder clearMovies() {
      if (moviesBuilder_ == null) {
        movies_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        moviesBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .Movie movies = 1;</code>
     */
    public Builder removeMovies(int index) {
      if (moviesBuilder_ == null) {
        ensureMoviesIsMutable();
        movies_.remove(index);
        onChanged();
      } else {
        moviesBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .Movie movies = 1;</code>
     */
    public project.javaOut.Movie.Builder getMoviesBuilder(
        int index) {
      return getMoviesFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .Movie movies = 1;</code>
     */
    public project.javaOut.MovieOrBuilder getMoviesOrBuilder(
        int index) {
      if (moviesBuilder_ == null) {
        return movies_.get(index);  } else {
        return moviesBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .Movie movies = 1;</code>
     */
    public java.util.List<? extends project.javaOut.MovieOrBuilder> 
         getMoviesOrBuilderList() {
      if (moviesBuilder_ != null) {
        return moviesBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(movies_);
      }
    }
    /**
     * <code>repeated .Movie movies = 1;</code>
     */
    public project.javaOut.Movie.Builder addMoviesBuilder() {
      return getMoviesFieldBuilder().addBuilder(
          project.javaOut.Movie.getDefaultInstance());
    }
    /**
     * <code>repeated .Movie movies = 1;</code>
     */
    public project.javaOut.Movie.Builder addMoviesBuilder(
        int index) {
      return getMoviesFieldBuilder().addBuilder(
          index, project.javaOut.Movie.getDefaultInstance());
    }
    /**
     * <code>repeated .Movie movies = 1;</code>
     */
    public java.util.List<project.javaOut.Movie.Builder> 
         getMoviesBuilderList() {
      return getMoviesFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        project.javaOut.Movie, project.javaOut.Movie.Builder, project.javaOut.MovieOrBuilder> 
        getMoviesFieldBuilder() {
      if (moviesBuilder_ == null) {
        moviesBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            project.javaOut.Movie, project.javaOut.Movie.Builder, project.javaOut.MovieOrBuilder>(
                movies_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        movies_ = null;
      }
      return moviesBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:MovieList)
  }

  // @@protoc_insertion_point(class_scope:MovieList)
  private static final project.javaOut.MovieList DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new project.javaOut.MovieList();
  }

  public static project.javaOut.MovieList getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<MovieList>
      PARSER = new com.google.protobuf.AbstractParser<MovieList>() {
    @java.lang.Override
    public MovieList parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new MovieList(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<MovieList> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<MovieList> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public project.javaOut.MovieList getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

