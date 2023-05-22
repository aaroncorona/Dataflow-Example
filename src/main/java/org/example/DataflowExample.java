package org.example;

import org.apache.beam.runners.dataflow.DataflowRunner;
import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

public class DataflowExample {
    public interface DataflowExampleOptions extends DataflowPipelineOptions {
        @Description("Path of the file to read from")
        @Default.String("gs://your-bucket/input.txt")
        String getInputFile();
        void setInputFile(String value);

        @Description("Path of the file to write to")
        @Default.String("gs://your-bucket/output.txt")
        String getOutputFile();
        void setOutputFile(String value);
    }

    public static void main(String[] args) {
        DataflowExampleOptions options = PipelineOptionsFactory.fromArgs(args)
                .as(DataflowExampleOptions.class);
        options.setRunner(DataflowRunner.class);

        Pipeline pipeline = Pipeline.create(options);

        pipeline
                .apply("ReadLines", TextIO.read().from(options.getInputFile()))
                // Apply transformations or processing as needed
                .apply("WriteOutput", TextIO.write().to(options.getOutputFile()));

        pipeline.run();
    }
}
