import io.airlift.airline.*;
import org.apache.commons.io.FileUtils;

import javax.inject.Inject;
import java.io.*;

/**
 * Created by mac on 8/24/16.
 */
public class Application {
    @Inject
    public HelpOption helpOption;

    public static void main(String... args) {
        Cli.CliBuilder<Runnable> builder = Cli.<Runnable>builder("csrtool")
                .withDescription("certificate signing request utility")
                .withDefaultCommand(Help.class)
                .withCommands(Help.class, CreateCsr.class);

        Cli<Runnable> csrToolParser = builder.build();
        csrToolParser.parse(args).run();

    }


    public static class CsrToolCommand implements Runnable {
        @Option(type = OptionType.GLOBAL, name = "-v", description = "Verbose mode")
        public boolean verbose;

        public void run() {
            System.out.println(">>" + getClass().getSimpleName());
        }
    }

    @Command(name = "create", description = "Add file contents to the index")
    public static class CreateCsr extends CsrToolCommand {

        @Option(name = {"-o", "--out"}, description = "Output CSR File name")
        public String outputFileName;

        @Override
        public void run() {
            super.run();

            CsrGenerator generator = new CsrGenerator();
            String csr = generator.create();

            if (outputFileName != null) {
                try {
                    FileUtils.writeStringToFile(new File(outputFileName), csr);
                    System.out.println("CSR is created.");
                    System.out.println("File Name: ".concat(outputFileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(csr);
            }
        }
    }

}

