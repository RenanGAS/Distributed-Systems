// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Movie.proto

package project.javaOut;

public interface MovieOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Movie)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string title = 1;</code>
   * @return The title.
   */
  java.lang.String getTitle();
  /**
   * <code>string title = 1;</code>
   * @return The bytes for title.
   */
  com.google.protobuf.ByteString
      getTitleBytes();

  /**
   * <code>optional int32 year = 2;</code>
   * @return Whether the year field is set.
   */
  boolean hasYear();
  /**
   * <code>optional int32 year = 2;</code>
   * @return The year.
   */
  int getYear();

  /**
   * <code>optional string released = 3;</code>
   * @return Whether the released field is set.
   */
  boolean hasReleased();
  /**
   * <code>optional string released = 3;</code>
   * @return The released.
   */
  java.lang.String getReleased();
  /**
   * <code>optional string released = 3;</code>
   * @return The bytes for released.
   */
  com.google.protobuf.ByteString
      getReleasedBytes();

  /**
   * <code>optional string poster = 4;</code>
   * @return Whether the poster field is set.
   */
  boolean hasPoster();
  /**
   * <code>optional string poster = 4;</code>
   * @return The poster.
   */
  java.lang.String getPoster();
  /**
   * <code>optional string poster = 4;</code>
   * @return The bytes for poster.
   */
  com.google.protobuf.ByteString
      getPosterBytes();

  /**
   * <code>optional string plot = 5;</code>
   * @return Whether the plot field is set.
   */
  boolean hasPlot();
  /**
   * <code>optional string plot = 5;</code>
   * @return The plot.
   */
  java.lang.String getPlot();
  /**
   * <code>optional string plot = 5;</code>
   * @return The bytes for plot.
   */
  com.google.protobuf.ByteString
      getPlotBytes();

  /**
   * <code>optional string fullplot = 6;</code>
   * @return Whether the fullplot field is set.
   */
  boolean hasFullplot();
  /**
   * <code>optional string fullplot = 6;</code>
   * @return The fullplot.
   */
  java.lang.String getFullplot();
  /**
   * <code>optional string fullplot = 6;</code>
   * @return The bytes for fullplot.
   */
  com.google.protobuf.ByteString
      getFullplotBytes();

  /**
   * <code>repeated string directors = 7;</code>
   * @return A list containing the directors.
   */
  java.util.List<java.lang.String>
      getDirectorsList();
  /**
   * <code>repeated string directors = 7;</code>
   * @return The count of directors.
   */
  int getDirectorsCount();
  /**
   * <code>repeated string directors = 7;</code>
   * @param index The index of the element to return.
   * @return The directors at the given index.
   */
  java.lang.String getDirectors(int index);
  /**
   * <code>repeated string directors = 7;</code>
   * @param index The index of the value to return.
   * @return The bytes of the directors at the given index.
   */
  com.google.protobuf.ByteString
      getDirectorsBytes(int index);

  /**
   * <code>repeated string cast = 8;</code>
   * @return A list containing the cast.
   */
  java.util.List<java.lang.String>
      getCastList();
  /**
   * <code>repeated string cast = 8;</code>
   * @return The count of cast.
   */
  int getCastCount();
  /**
   * <code>repeated string cast = 8;</code>
   * @param index The index of the element to return.
   * @return The cast at the given index.
   */
  java.lang.String getCast(int index);
  /**
   * <code>repeated string cast = 8;</code>
   * @param index The index of the value to return.
   * @return The bytes of the cast at the given index.
   */
  com.google.protobuf.ByteString
      getCastBytes(int index);

  /**
   * <code>repeated string contries = 9;</code>
   * @return A list containing the contries.
   */
  java.util.List<java.lang.String>
      getContriesList();
  /**
   * <code>repeated string contries = 9;</code>
   * @return The count of contries.
   */
  int getContriesCount();
  /**
   * <code>repeated string contries = 9;</code>
   * @param index The index of the element to return.
   * @return The contries at the given index.
   */
  java.lang.String getContries(int index);
  /**
   * <code>repeated string contries = 9;</code>
   * @param index The index of the value to return.
   * @return The bytes of the contries at the given index.
   */
  com.google.protobuf.ByteString
      getContriesBytes(int index);

  /**
   * <code>repeated string genres = 10;</code>
   * @return A list containing the genres.
   */
  java.util.List<java.lang.String>
      getGenresList();
  /**
   * <code>repeated string genres = 10;</code>
   * @return The count of genres.
   */
  int getGenresCount();
  /**
   * <code>repeated string genres = 10;</code>
   * @param index The index of the element to return.
   * @return The genres at the given index.
   */
  java.lang.String getGenres(int index);
  /**
   * <code>repeated string genres = 10;</code>
   * @param index The index of the value to return.
   * @return The bytes of the genres at the given index.
   */
  com.google.protobuf.ByteString
      getGenresBytes(int index);
}
