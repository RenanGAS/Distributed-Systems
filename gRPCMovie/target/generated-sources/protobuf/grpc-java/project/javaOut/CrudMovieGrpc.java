package project.javaOut;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.39.0)",
    comments = "Source: Movie.proto")
public final class CrudMovieGrpc {

  private CrudMovieGrpc() {}

  public static final String SERVICE_NAME = "CrudMovie";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<project.javaOut.Movie,
      project.javaOut.ResponseMsg> getCreateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Create",
      requestType = project.javaOut.Movie.class,
      responseType = project.javaOut.ResponseMsg.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<project.javaOut.Movie,
      project.javaOut.ResponseMsg> getCreateMethod() {
    io.grpc.MethodDescriptor<project.javaOut.Movie, project.javaOut.ResponseMsg> getCreateMethod;
    if ((getCreateMethod = CrudMovieGrpc.getCreateMethod) == null) {
      synchronized (CrudMovieGrpc.class) {
        if ((getCreateMethod = CrudMovieGrpc.getCreateMethod) == null) {
          CrudMovieGrpc.getCreateMethod = getCreateMethod =
              io.grpc.MethodDescriptor.<project.javaOut.Movie, project.javaOut.ResponseMsg>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Create"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  project.javaOut.Movie.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  project.javaOut.ResponseMsg.getDefaultInstance()))
              .setSchemaDescriptor(new CrudMovieMethodDescriptorSupplier("Create"))
              .build();
        }
      }
    }
    return getCreateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<project.javaOut.SearchParam,
      project.javaOut.Movie> getReadMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Read",
      requestType = project.javaOut.SearchParam.class,
      responseType = project.javaOut.Movie.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<project.javaOut.SearchParam,
      project.javaOut.Movie> getReadMethod() {
    io.grpc.MethodDescriptor<project.javaOut.SearchParam, project.javaOut.Movie> getReadMethod;
    if ((getReadMethod = CrudMovieGrpc.getReadMethod) == null) {
      synchronized (CrudMovieGrpc.class) {
        if ((getReadMethod = CrudMovieGrpc.getReadMethod) == null) {
          CrudMovieGrpc.getReadMethod = getReadMethod =
              io.grpc.MethodDescriptor.<project.javaOut.SearchParam, project.javaOut.Movie>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Read"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  project.javaOut.SearchParam.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  project.javaOut.Movie.getDefaultInstance()))
              .setSchemaDescriptor(new CrudMovieMethodDescriptorSupplier("Read"))
              .build();
        }
      }
    }
    return getReadMethod;
  }

  private static volatile io.grpc.MethodDescriptor<project.javaOut.Movie,
      project.javaOut.Movie> getUpdateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Update",
      requestType = project.javaOut.Movie.class,
      responseType = project.javaOut.Movie.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<project.javaOut.Movie,
      project.javaOut.Movie> getUpdateMethod() {
    io.grpc.MethodDescriptor<project.javaOut.Movie, project.javaOut.Movie> getUpdateMethod;
    if ((getUpdateMethod = CrudMovieGrpc.getUpdateMethod) == null) {
      synchronized (CrudMovieGrpc.class) {
        if ((getUpdateMethod = CrudMovieGrpc.getUpdateMethod) == null) {
          CrudMovieGrpc.getUpdateMethod = getUpdateMethod =
              io.grpc.MethodDescriptor.<project.javaOut.Movie, project.javaOut.Movie>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Update"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  project.javaOut.Movie.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  project.javaOut.Movie.getDefaultInstance()))
              .setSchemaDescriptor(new CrudMovieMethodDescriptorSupplier("Update"))
              .build();
        }
      }
    }
    return getUpdateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<project.javaOut.SearchParam,
      project.javaOut.ResponseMsg> getDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Delete",
      requestType = project.javaOut.SearchParam.class,
      responseType = project.javaOut.ResponseMsg.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<project.javaOut.SearchParam,
      project.javaOut.ResponseMsg> getDeleteMethod() {
    io.grpc.MethodDescriptor<project.javaOut.SearchParam, project.javaOut.ResponseMsg> getDeleteMethod;
    if ((getDeleteMethod = CrudMovieGrpc.getDeleteMethod) == null) {
      synchronized (CrudMovieGrpc.class) {
        if ((getDeleteMethod = CrudMovieGrpc.getDeleteMethod) == null) {
          CrudMovieGrpc.getDeleteMethod = getDeleteMethod =
              io.grpc.MethodDescriptor.<project.javaOut.SearchParam, project.javaOut.ResponseMsg>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  project.javaOut.SearchParam.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  project.javaOut.ResponseMsg.getDefaultInstance()))
              .setSchemaDescriptor(new CrudMovieMethodDescriptorSupplier("Delete"))
              .build();
        }
      }
    }
    return getDeleteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<project.javaOut.SearchParam,
      project.javaOut.MovieList> getListByActorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListByActor",
      requestType = project.javaOut.SearchParam.class,
      responseType = project.javaOut.MovieList.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<project.javaOut.SearchParam,
      project.javaOut.MovieList> getListByActorMethod() {
    io.grpc.MethodDescriptor<project.javaOut.SearchParam, project.javaOut.MovieList> getListByActorMethod;
    if ((getListByActorMethod = CrudMovieGrpc.getListByActorMethod) == null) {
      synchronized (CrudMovieGrpc.class) {
        if ((getListByActorMethod = CrudMovieGrpc.getListByActorMethod) == null) {
          CrudMovieGrpc.getListByActorMethod = getListByActorMethod =
              io.grpc.MethodDescriptor.<project.javaOut.SearchParam, project.javaOut.MovieList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListByActor"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  project.javaOut.SearchParam.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  project.javaOut.MovieList.getDefaultInstance()))
              .setSchemaDescriptor(new CrudMovieMethodDescriptorSupplier("ListByActor"))
              .build();
        }
      }
    }
    return getListByActorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<project.javaOut.SearchParam,
      project.javaOut.MovieList> getListByGenreMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListByGenre",
      requestType = project.javaOut.SearchParam.class,
      responseType = project.javaOut.MovieList.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<project.javaOut.SearchParam,
      project.javaOut.MovieList> getListByGenreMethod() {
    io.grpc.MethodDescriptor<project.javaOut.SearchParam, project.javaOut.MovieList> getListByGenreMethod;
    if ((getListByGenreMethod = CrudMovieGrpc.getListByGenreMethod) == null) {
      synchronized (CrudMovieGrpc.class) {
        if ((getListByGenreMethod = CrudMovieGrpc.getListByGenreMethod) == null) {
          CrudMovieGrpc.getListByGenreMethod = getListByGenreMethod =
              io.grpc.MethodDescriptor.<project.javaOut.SearchParam, project.javaOut.MovieList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListByGenre"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  project.javaOut.SearchParam.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  project.javaOut.MovieList.getDefaultInstance()))
              .setSchemaDescriptor(new CrudMovieMethodDescriptorSupplier("ListByGenre"))
              .build();
        }
      }
    }
    return getListByGenreMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CrudMovieStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CrudMovieStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CrudMovieStub>() {
        @java.lang.Override
        public CrudMovieStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CrudMovieStub(channel, callOptions);
        }
      };
    return CrudMovieStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CrudMovieBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CrudMovieBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CrudMovieBlockingStub>() {
        @java.lang.Override
        public CrudMovieBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CrudMovieBlockingStub(channel, callOptions);
        }
      };
    return CrudMovieBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CrudMovieFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CrudMovieFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CrudMovieFutureStub>() {
        @java.lang.Override
        public CrudMovieFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CrudMovieFutureStub(channel, callOptions);
        }
      };
    return CrudMovieFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class CrudMovieImplBase implements io.grpc.BindableService {

    /**
     */
    public void create(project.javaOut.Movie request,
        io.grpc.stub.StreamObserver<project.javaOut.ResponseMsg> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateMethod(), responseObserver);
    }

    /**
     */
    public void read(project.javaOut.SearchParam request,
        io.grpc.stub.StreamObserver<project.javaOut.Movie> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReadMethod(), responseObserver);
    }

    /**
     */
    public void update(project.javaOut.Movie request,
        io.grpc.stub.StreamObserver<project.javaOut.Movie> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateMethod(), responseObserver);
    }

    /**
     */
    public void delete(project.javaOut.SearchParam request,
        io.grpc.stub.StreamObserver<project.javaOut.ResponseMsg> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteMethod(), responseObserver);
    }

    /**
     */
    public void listByActor(project.javaOut.SearchParam request,
        io.grpc.stub.StreamObserver<project.javaOut.MovieList> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListByActorMethod(), responseObserver);
    }

    /**
     */
    public void listByGenre(project.javaOut.SearchParam request,
        io.grpc.stub.StreamObserver<project.javaOut.MovieList> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListByGenreMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                project.javaOut.Movie,
                project.javaOut.ResponseMsg>(
                  this, METHODID_CREATE)))
          .addMethod(
            getReadMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                project.javaOut.SearchParam,
                project.javaOut.Movie>(
                  this, METHODID_READ)))
          .addMethod(
            getUpdateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                project.javaOut.Movie,
                project.javaOut.Movie>(
                  this, METHODID_UPDATE)))
          .addMethod(
            getDeleteMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                project.javaOut.SearchParam,
                project.javaOut.ResponseMsg>(
                  this, METHODID_DELETE)))
          .addMethod(
            getListByActorMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                project.javaOut.SearchParam,
                project.javaOut.MovieList>(
                  this, METHODID_LIST_BY_ACTOR)))
          .addMethod(
            getListByGenreMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                project.javaOut.SearchParam,
                project.javaOut.MovieList>(
                  this, METHODID_LIST_BY_GENRE)))
          .build();
    }
  }

  /**
   */
  public static final class CrudMovieStub extends io.grpc.stub.AbstractAsyncStub<CrudMovieStub> {
    private CrudMovieStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CrudMovieStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CrudMovieStub(channel, callOptions);
    }

    /**
     */
    public void create(project.javaOut.Movie request,
        io.grpc.stub.StreamObserver<project.javaOut.ResponseMsg> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void read(project.javaOut.SearchParam request,
        io.grpc.stub.StreamObserver<project.javaOut.Movie> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReadMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void update(project.javaOut.Movie request,
        io.grpc.stub.StreamObserver<project.javaOut.Movie> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void delete(project.javaOut.SearchParam request,
        io.grpc.stub.StreamObserver<project.javaOut.ResponseMsg> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listByActor(project.javaOut.SearchParam request,
        io.grpc.stub.StreamObserver<project.javaOut.MovieList> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListByActorMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listByGenre(project.javaOut.SearchParam request,
        io.grpc.stub.StreamObserver<project.javaOut.MovieList> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListByGenreMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CrudMovieBlockingStub extends io.grpc.stub.AbstractBlockingStub<CrudMovieBlockingStub> {
    private CrudMovieBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CrudMovieBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CrudMovieBlockingStub(channel, callOptions);
    }

    /**
     */
    public project.javaOut.ResponseMsg create(project.javaOut.Movie request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateMethod(), getCallOptions(), request);
    }

    /**
     */
    public project.javaOut.Movie read(project.javaOut.SearchParam request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReadMethod(), getCallOptions(), request);
    }

    /**
     */
    public project.javaOut.Movie update(project.javaOut.Movie request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateMethod(), getCallOptions(), request);
    }

    /**
     */
    public project.javaOut.ResponseMsg delete(project.javaOut.SearchParam request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteMethod(), getCallOptions(), request);
    }

    /**
     */
    public project.javaOut.MovieList listByActor(project.javaOut.SearchParam request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListByActorMethod(), getCallOptions(), request);
    }

    /**
     */
    public project.javaOut.MovieList listByGenre(project.javaOut.SearchParam request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListByGenreMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CrudMovieFutureStub extends io.grpc.stub.AbstractFutureStub<CrudMovieFutureStub> {
    private CrudMovieFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CrudMovieFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CrudMovieFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<project.javaOut.ResponseMsg> create(
        project.javaOut.Movie request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<project.javaOut.Movie> read(
        project.javaOut.SearchParam request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReadMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<project.javaOut.Movie> update(
        project.javaOut.Movie request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<project.javaOut.ResponseMsg> delete(
        project.javaOut.SearchParam request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<project.javaOut.MovieList> listByActor(
        project.javaOut.SearchParam request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListByActorMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<project.javaOut.MovieList> listByGenre(
        project.javaOut.SearchParam request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListByGenreMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE = 0;
  private static final int METHODID_READ = 1;
  private static final int METHODID_UPDATE = 2;
  private static final int METHODID_DELETE = 3;
  private static final int METHODID_LIST_BY_ACTOR = 4;
  private static final int METHODID_LIST_BY_GENRE = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CrudMovieImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CrudMovieImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE:
          serviceImpl.create((project.javaOut.Movie) request,
              (io.grpc.stub.StreamObserver<project.javaOut.ResponseMsg>) responseObserver);
          break;
        case METHODID_READ:
          serviceImpl.read((project.javaOut.SearchParam) request,
              (io.grpc.stub.StreamObserver<project.javaOut.Movie>) responseObserver);
          break;
        case METHODID_UPDATE:
          serviceImpl.update((project.javaOut.Movie) request,
              (io.grpc.stub.StreamObserver<project.javaOut.Movie>) responseObserver);
          break;
        case METHODID_DELETE:
          serviceImpl.delete((project.javaOut.SearchParam) request,
              (io.grpc.stub.StreamObserver<project.javaOut.ResponseMsg>) responseObserver);
          break;
        case METHODID_LIST_BY_ACTOR:
          serviceImpl.listByActor((project.javaOut.SearchParam) request,
              (io.grpc.stub.StreamObserver<project.javaOut.MovieList>) responseObserver);
          break;
        case METHODID_LIST_BY_GENRE:
          serviceImpl.listByGenre((project.javaOut.SearchParam) request,
              (io.grpc.stub.StreamObserver<project.javaOut.MovieList>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class CrudMovieBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CrudMovieBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return project.javaOut.CRUDMovie.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CrudMovie");
    }
  }

  private static final class CrudMovieFileDescriptorSupplier
      extends CrudMovieBaseDescriptorSupplier {
    CrudMovieFileDescriptorSupplier() {}
  }

  private static final class CrudMovieMethodDescriptorSupplier
      extends CrudMovieBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CrudMovieMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CrudMovieGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CrudMovieFileDescriptorSupplier())
              .addMethod(getCreateMethod())
              .addMethod(getReadMethod())
              .addMethod(getUpdateMethod())
              .addMethod(getDeleteMethod())
              .addMethod(getListByActorMethod())
              .addMethod(getListByGenreMethod())
              .build();
        }
      }
    }
    return result;
  }
}
