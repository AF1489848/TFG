package Functions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class generarScriptSlurm {
    public static void generarScript(String scriptPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scriptPath))) {
            writer.write("#!/bin/bash -l");
            writer.newLine();
            writer.newLine();
            writer.write("#SBATCH --job-name=\"AC-red\"\n" +
                             "#SBATCH -w stanlee\n" +
                             "#SBATCH --exclusive\n" +
                             "#SBATCH --time=1200:00:00\n" +
                             "#SBATCH --nodes=1\n" +
                             "#SBATCH --ntasks=1\n" +
                             "#SBATCH --cpus-per-task=128\n" +
                             "#SBATCH --output=stanlee-red-OUT-%j.log\n" +
                             "#SBATCH --error=stanlee-red-ERR-%j.log");
            writer.newLine();
            writer.newLine();
            writer.write("#module load gcc/7.3.0n\n" +
                             "module load gcc/9.2.0\n" +
                             "module load likwid/5.1.2\n" +
                             "module load openmpi/3.0.4\n" +
                             "module load conda");
            writer.newLine();
            writer.newLine();
            writer.write("cd\n" +
                             "source $HOME/.bashrc\n" +
                             "source $HOME/tools/spack/share/spack/setup-env.sh\n" +
                             "#spack load python arch=linux-centos7-sandybridge\n" +
                             "spack load python arch=`spack arch`");
            writer.newLine();
            writer.newLine();
            writer.write("#mkdir openmp");
            writer.newLine();
            writer.write("cd /home/suren/legacy_tools/stream_modified/amd_stanlee");
            writer.newLine();
            writer.write("#rm time.log");
            writer.newLine();
            writer.write("#Delete the existing directories");
            writer.newLine();
            writer.write("rm -r close_ctrs_stats \n" +
                             "rm -r spread_ctrs_stats");
            writer.newLine();
            writer.write("rm -r close_ctrs_threads\n" +
                             "rm -r spread_ctrs_threads");
            writer.newLine();
            writer.write("rm -r close_metrics_stats\n" +
                             "rm -r spread_metrics_stats");
            writer.newLine();
            writer.write("rm -r close_metrics_threads\n" +
                             "rm -r spread_metrics_threads");
            writer.newLine();
            writer.write("rm -r close_raw\n" +
                             "rm -r spread_raw");
            writer.newLine();
            writer.write("#Create the directories\n" +
                             "mkdir close_ctrs_stats\n" +
                             "mkdir spread_ctrs_stats");
            writer.newLine();
            writer.write("mkdir close_ctrs_threads\n" +
                             "mkdir spread_ctrs_threads");
            writer.newLine();
            writer.write("mkdir close_metrics_stats\n" +
                             "mkdir spread_metrics_stats");
            writer.newLine();
            writer.write("mkdir close_metrics_threads\n" +
                             "mkdir spread_metrics_threads");
            writer.newLine();
            writer.write("mkdir close_raw\n" +
                             "mkdir spread_raw");
            writer.newLine();
            writer.write("#Copy the \n" +
                             "cp $HOME/legacy_tools/scripts/join.py close_ctrs_stats/join.py\n" +
                             "cp $HOME/legacy_tools/scripts/join.py spread_ctrs_stats/join.py");
            writer.newLine();
            writer.write("cp $HOME/legacy_tools/scripts/join-close-spread.py spread_ctrs_stats/join-close-spread.py\n" +
                             "cp $HOME/legacy_tools/scripts/join-close-spread.py join-close-spread.py");
            writer.newLine();
            writer.write("cp $HOME/legacy_tools/scripts/Create_T_list.ipynb Create_T_list.ipynb\n" +
                             "cp $HOME/legacy_tools/scripts/Create_T_list-Penguin-Modded-order.ipynb Create_T_list-Penguin-Modded-order.ipynb\n");
            writer.newLine();
            writer.write("#Modify this to the number of cores in your system, cores instead of threads.\n" +
                             "#todo modify accordig to stanlee nums\n" +
                             "#USE THIS FOR THE CLOSED CASE\n" +
                             "export OMP_PLACES=\"{0},{8},{16},{24},{32},{40},{48},{56},{2},{10},{18},{26},{34},{42},{50},{58},{4},{12},{20},{28},{36},{44},{52},{60},{6},{14},{22},{30},{38},{46},{54},{62},{1},{9},{17},{25},{33},{41},{49},{57},{3},{11},{19},{27},{35},{43},{51},{59},{5},{13},{21},{29},{37},{45},{53},{61},{7},{15},{23},{31},{39},{47},{55},{63}\"\n" +
                             "export OMP_PROC_BIND=close");
            writer.newLine();
            writer.write("##Prova likwid\n" +
                             "for tun in BRANCH #CACHE CLOCK CPI DATA DIVIDE ENERGY FLOPS_DP FLOPS_SP ICACHE L2 L3 MEM MEM_DP MEM_SP NUMA TLB\n" +
                             "do");
            writer.newLine();
            writer.write("      echo $tun\n" +
                             "      #Modify this to the number of cores in your system, cores instead of threads.\n" +
                             "      #todo modify 1..64 instead\n" +
                             "      for threads in {7..64} ## 64\n" +
                             "      do");
            writer.newLine();
            writer.write("                echo $threads\n" +
                    "                export OMP_NUM_THREADS=$threads\n");
            writer.newLine();
            writer.write("\t\t#USE THESE SIZES FOR STANLEE\n" +
                    "                #for size in 1024\n" +
                    "                for size in 7936 15872 23808 31744 39680 47616 #55552 63488 71424 79360 87296 95232 103168 111104 119040 126976 134912 142848 150784 158720 166656 174592 182528 190464 198400 206336 214272 222208 230144 #238080 246016 253952 261888 392960 523776 654592 785408 916224 1047040 1177856 1308672 1439488 1570304 1701120 1831936 1962752 2093568 2224384 2355200 2486016 2616832 2747648 2878464 3009280 3140096 3270912 3401728 3532544 3663360 3794176 3924992 4055808 4186624 4587520 5636096 6684672 7733248 8781824 9830400 10878976 11927552 12976128 14024704 15073280 16121856 16777216 33554432 75497472\n" +
                    "                do\n" +
                    "\t\t\techo $size\n" +
                    "\t\t\texport size=$size\n" +
                    "                        echo \"$tun $threads $size\"\n" +
                    "\t\t\tgcc -llikwid -lm -fopenmp -pthread -o stream-likwid stream-likwid.c -O2 -DN=$size -DNTIMES=1 -DNTHREADS=$threads -DLIKWID_PERFMON -I /lex/software/LIBRARIES/likwid-5.2.1/include -L /lex/software/LIBRARIES/likwid-5.2.1/lib;\t\n" +
                    "\t\t\tfor kernel  in {0..33} \t\n" +
                    "\t\t\tdo \n" +
                    "\t\t\t\tfor times in {1..100}\n" +
                    "\t\t\t\tdo\n" +
                    "\t\t\t\tlikwid-perfctr -g $tun -m -o data.csv -O ./stream-likwid $kernel\n" +
                    "                \t\tcat data.csv >> data_raw.csv \n" +
                    "\t\t\t\tcat data.csv >> data_raw_threads.csv\n" +
                    "\t\t\t\tdone\n");
            writer.newLine();
            writer.write("\t\t\t\tpython3 likwid_environment_counters_threads.py \n" +
                    "\t\t\t\tcat data_ctrs_threads.csv >> ${threads}-data-O2-${size}_ctrs_threads.csv\n");
            writer.newLine();
            writer.write("\t\t\t\tpython3 likwid_environment_metrics_threads.py\n" +
                    "\t\t\t\tcat data_metrics_threads.csv >> ${threads}-data-O2-${size}_metrics_threads.csv\t\n");
            writer.newLine();
            writer.write("\t\t\t\trm data_raw_threads.csv\n" +
                    "\t\t\t\trm data_ctrs_threads.csv\n");
            writer.newLine();
            writer.write("\t\t\tdone\n" +
                    "\t\tpython3 likwid_environment_counters_stats.py\n" +
                    "\t\tpython3 likwid_environment_metrics_stats.py\n");
            writer.newLine();
            writer.write("\t\tmv data_raw.csv ${threads}-data-O2-${size}_raw.csv #Create the raw datasets\n");
            writer.newLine();
            writer.write("\t\tcat ${threads}-data-O2-${size}_ctrs_threads.csv >> data_ctrs_threads_header.csv\n" +
                    "\t\tmv data_ctrs_threads_header.csv ${threads}-data-O2-${size}_ctrs_threads.csv\n");
            writer.newLine();
            writer.write("\t\tcat ${threads}-data-O2-${size}_metrics_threads.csv >> data_metrics_threads_header.csv\n" +
                    "                mv data_metrics_threads_header.csv ${threads}-data-O2-${size}_metrics_threads.csv\t\n");
            writer.newLine();
            writer.write("\t\trm data*                #Del old data\n" +
                    "\t\tdone\n");
            writer.newLine();
            writer.write("\tmkdir -p close_raw/$tun\n" +
                    "        mv *raw.csv close_raw/$tun\n");
            writer.newLine();
            writer.write("\tmkdir -p close_ctrs_threads/$tun\n" +
                    "\t#sed -i 1i\"id;data_size;comp_opt;threads;label;Event;HWThread 0;\" ${threads}-data-O2-${size}_ctrs_threads.csv  \n" +
                    "        mv *ctrs_threads.csv close_ctrs_threads/$tun\n");
            writer.newLine();
            writer.write("\tmkdir -p close_metrics_threads/$tun\n" +
                    "        #sed -i 1i\"id;data_size;comp_opt;threads;label;Event;HWThread 0;\" ${threads}-data-O2-${size}_ctrs_threads.csv\n" +
                    "        mv *metrics_threads.csv close_metrics_threads/$tun\n");
            writer.newLine();
            writer.write("\tmkdir -p close_metrics_stats/$tun\n" +
                    "        mv *metrics.csv close_metrics_stats/$tun\n");
            writer.newLine();
            writer.write("        mkdir -p close_ctrs_stats/$tun\n" +
                    "        mv *.csv close_ctrs_stats/$tun\n" +
                    "\tdone\n" +
                    "done\n");
            writer.newLine();
            writer.newLine();
            writer.newLine();
            writer.newLine();
            writer.write("#USE THIS FOR THE SPREAD CASE\n" +
                    "export OMP_PLACES=\"{0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11},{12},{13},{14},{15},{16},{17},{18},{19},{20},{21},{22},{23},{24},{25},{26},{27},{28},{29},{30},{31},{32},{33},{34},{35},{36},{37},{38},{39},{40},{41},{42},{43},{44},{45},{46},{47},{48},{49},{50},{51},{52},{53},{54},{55},{56},{57},{58},{59},{60},{61},{62},{63}\"\n" +
                    "export OMP_PROC_BIND=close\n");
            writer.newLine();
            writer.write("##Prova likwid\n" +
                    "for tun in BRANCH #CACHE CLOCK CPI DATA DIVIDE ENERGY FLOPS_DP FLOPS_SP ICACHE L2 L3 MEM MEM_DP MEM_SP NUMA TLB\n" +
                    "do\n" +
                    "        echo $tun\n" +
                    "        #Modify this to the number of cores in your system, cores instead of threads.\n" +
                    "        #todo modify 1..64 instead\n" +
                    "        for threads in {1..64}\n" +
                    "        do\n" +
                    "                echo $threads\n" +
                    "                export OMP_NUM_THREADS=$threads\n");
            writer.newLine();
            writer.write("                #USE THESE SIZES FOR STANLEE\n" +
                    "                #for size in 1024\n" +
                    "                for size in 7936 15872 23808 31744 39680 47616 #55552 63488 71424 79360 87296 95232 103168 111104 119040 126976 134912 142848 150784 158720 166656 174592 182528 190464 198400 206336 214272 222208 230144 #238080 246016 253952 261888 392960 523776 654592 785408 916224 1047040 1177856 1308672 1439488 1570304 1701120 1831936 1962752 2093568 2224384 2355200 2486016 2616832 2747648 2878464 3009280 3140096 3270912 3401728 3532544 3663360 3794176 3924992 4055808 4186624 4587520 5636096 6684672 7733248 8781824 9830400 10878976 11927552 12976128 14024704 15073280 16121856 16777216 33554432 75497472\n" +
                    "                do\n" +
                    "                        echo $size\n" +
                    "                        export size=$size\n" +
                    "                        echo \"$tun $threads $size\"\n" +
                    "                        gcc -llikwid -lm -fopenmp -pthread -o stream-likwid stream-likwid.c -O2 -DN=$size -DNTIMES=1 -DNTHREADS=$threads -DLIKWID_PERFMON -I /lex/software/LIBRARIES/likwid-5.2.1/include -L /lex/software/LIBRARIES/likwid-5.2.1/lib;\n" +
                    "                        for kernel  in {0..33}\n" +
                    "                        do\n" +
                    "\t\t\t\tfor times in {1..100}\n" +
                    "                                do\n" +
                    "                                likwid-perfctr -g $tun -m -o data.csv -O ./stream-likwid $kernel\n" +
                    "                                cat data.csv >> data_raw.csv\n" +
                    "                                cat data.csv >> data_raw_threads.csv\n" +
                    "                                done\n");
            writer.newLine();
            writer.write("                                python3 likwid_environment_counters_threads.py\n" +
                    "                                cat data_ctrs_threads.csv >> ${threads}-data-O2-${size}_ctrs_threads.csv\n");
            writer.newLine();
            writer.write("                                python3 likwid_environment_metrics_threads.py\n" +
                    "                                cat data_metrics_threads.csv >> ${threads}-data-O2-${size}_metrics_threads.csv\n");
            writer.newLine();
            writer.write("                                rm data_raw_threads.csv\n" +
                    "                                rm data_ctrs_threads.csv\n" +
                    "                        done\n" +
                    "\t\tpython3 likwid_environment_counters_stats.py\n" +
                    "                python3 likwid_environment_metrics_stats.py\n");
            writer.newLine();
            writer.write("                mv data_raw.csv ${threads}-data-O2-${size}_raw.csv #Create the raw datasets\n");
            writer.newLine();
            writer.write("                cat ${threads}-data-O2-${size}_ctrs_threads.csv >> data_ctrs_threads_header.csv\n" +
                    "                mv data_ctrs_threads_header.csv ${threads}-data-O2-${size}_ctrs_threads.csv\n");
            writer.newLine();
            writer.write("                cat ${threads}-data-O2-${size}_metrics_threads.csv >> data_metrics_threads_header.csv\n" +
                    "                mv data_metrics_threads_header.csv ${threads}-data-O2-${size}_metrics_threads.csv\n");
            writer.newLine();
            writer.write("                rm data*                #Del old data\n" +
                    "                done\n");
            writer.newLine();
            writer.write("\tmkdir -p spread_raw/$tun\n" +
                    "        mv *raw.csv spread_raw/$tun\n");
            writer.newLine();
            writer.write("\tmkdir -p spread_ctrs_threads/$tun\n" +
                    "        #sed -i 1i\"id;data_size;comp_opt;threads;label;Event;HWThread 0;\" ${threads}-data-O2-${size}_ctrs_threads.csv\n" +
                    "\tmv *ctrs_threads.csv spread_ctrs_threads/$tun\n");
            writer.newLine();
            writer.write("\tmkdir -p spread_metrics_threads/$tun\n" +
                    "        #sed -i 1i\"id;data_size;comp_opt;threads;label;Event;HWThread 0;\" ${threads}-data-O2-${size}_ctrs_threads.csv\n" +
                    "        mv *metrics_threads.csv spread_metrics_threads/$tun\n");
            writer.newLine();
            writer.write("\tmkdir -p spread_metrics_stats/$tun\n" +
                    "        mv *metrics.csv spread_metrics_stats/$tun\n");
            writer.newLine();
            writer.write("        mkdir -p spread_ctrs_stats/$tun\n" +
                    "        mv *.csv spread_ctrs_stats/$tun\n" +
                    "        done\n" +
                    "done\n");
            writer.newLine();
            writer.write("#mkdir close-backup\n" +
                    "#mkdir spread-backup\n" +
                    "\n" +
                    "#cp -r close/* close-backup/\n" +
                    "#cp -r spread/* spread-backup/\n" +
                    "#cp time.log close/time.log\n" +
                    "#cp time.log spread/time.log\n" +
                    "\n" +
                    "#source $HOME/tools/spack/share/spack/setup-env.sh\n" +
                    "#spack load python arch=linux-centos7-sandybridge\n" +
                    "#spack load python arch=`spack arch`\n" +
                    "\n" +
                    "#cd close\n" +
                    "#python3 join.py\n" +
                    "\n" +
                    "#cd ..\n" +
                    "\n" +
                    "#cd spread\n" +
                    "#python3 join.py\n" +
                    "\n" +
                    "#cd ..\n" +
                    "#python3 join-close-spread.py --close close/threads-data-partial.csv --spread spread/threads-data-partial.csv --benchname openmp\n" +
                    "\n");
            writer.newLine();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

