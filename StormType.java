import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;

/**
 * @author lakshman
 * 
 */
public class StormType {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.err.println("Usage: java StormType filename.csv output.csv");
			return;
		}
		BufferedReader reader = null;
		PrintWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader(args[0]));
			writer = new PrintWriter(new FileWriter(args[1]));
			Stat stat = new Stat();
			
			String line = null;
			float[] inputs = new float[23];
			boolean statAvailable = false;
			while ( (line = reader.readLine()) != null ){
				String[] columns = line.split(",");
				for (int i=0; i < columns.length; ++i){
					inputs[i] = Float.parseFloat(columns[i]);
				}
				int alg_stormtype = computeStormType(inputs);
				writer.println(alg_stormtype); // write out value for this column
				
				// Is the true value available?
				if ( columns.length == inputs.length ){
					int expectedValue = Integer.parseInt(columns[columns.length-1]);
					stat.update( expectedValue, alg_stormtype );
					statAvailable = true;
				}
			}
			
			if ( statAvailable ){
				System.out.println(stat);
			}
			
		} finally {
			if ( reader != null ){
				reader.close();
			}
			if ( writer != null ){
				writer.close();
			}
		}
	}

	/**
	 * Computes and maintains statistics
	 */
	public static class Stat
	{
		private int[][] stats = new int[5][5];
		public void update(int expectedValue, int alg_stormtype) {
			stats[expectedValue][alg_stormtype] ++;
		}
		
		public String toString()
		{
			String newline = System.getProperty("line.separator");
			DecimalFormat idf = new DecimalFormat("  ");
			DecimalFormat adf = new DecimalFormat("##.0");
			// Header
			StringBuilder sb = new StringBuilder();
			sb.append("       Got->  ");
			for (int i=0; i < stats.length; ++i){
				sb.append( idf.format(i) ).append("\t");
			}
			sb.append("Accuracy").append(newline);
			// Matrix
			for (int i=0; i < stats.length; ++i){
				sb.append( "Expected ").append(idf.format(i)).append(": ");
				float denom = 0;
				for (int j=0; j < stats[i].length; ++j){
					sb.append( idf.format(stats[i][j]) ).append("\t");
					denom += stats[i][j];
				}
				float acc = 100 * stats[i][i] / denom;
				sb.append( adf.format(acc) ).append(newline);
			}
			
			// Trailer
			sb.append("Accuracy        ");
			for (int i=0; i < stats.length; ++i){
				float denom = 0;
				for (int j=0; j < stats[i].length; ++j){
					denom += stats[j][i];
				}
				float acc = 100 * stats[i][i] / denom;
				sb.append( adf.format(acc) ).append("\t");
			}
			
			// Final
			sb.append(newline).append("True Skill Score (TSS)=").append( getTSS() ).append(newline);
			
			return sb.toString();
		}

		/** Computes True Skill Score (Hanssen/Kuipers/Peirces' skill score) */
		private float getTSS() {
			float sum_a = 0;
			float sum_b = 0;
			float sum_c = 0;
			int numCategories = stats.length;
			float[] numForecasts = new float[numCategories];
			float[] numObservations = new float[numCategories];
			for (int i=0; i < numCategories; ++i){
				for (int j=0; j < numCategories; ++j){
					// stats is stats[observed][forecast]
					numForecasts[i] += stats[j][i];
					numObservations[i] += stats[i][j];
				}
			}
			int N = 0;
			for (int i=0; i < numCategories; ++i){
				N += numForecasts[i];
			}
			for (int j=0; j < numCategories; ++j){
				sum_a += stats[j][j];
				sum_b += numForecasts[j] * numObservations[j];
				sum_c += numForecasts[j] * numForecasts[j];
			}
			float TSS = ((sum_a/N) - (sum_b)/(N*N))/(1 - sum_c/(N*N));
			return TSS;
		}
	}
	
	/**
	 * Result of Quinlan's decision tree algorithm
	 * @param input
	 * @return stormtype
	 */
	private static int computeStormType(float[] input) {

		if ((input[11]) < 11.4306) {
			if ((input[11]) < 3.51456) {
				return 0;
			} else {
				if ((input[12]) < 38.3377) {
					if ((input[21]) < 14.7142) {
						if ((input[12]) < 29.0601) {
							return 0;
						} else {
							if ((input[0]) < 2.78166) {
								return 0;
							} else {
								return 4;
							}
						}
					} else {
						if ((input[11]) < 6.52937) {
							if ((input[20]) < 227.065) {
								if ((input[12]) < 30.4995) {
									return 4;
								} else {
									if ((input[12]) < 36.1114) {
										return 0;
									} else {
										return 4;
									}
								}
							} else {
								return 0;
							}
						} else {
							return 4;
						}
					}
				} else {
					if ((input[11]) < 8.25122) {
						return 0;
					} else {
						if ((input[8]) < 0.003965) {
							if ((input[8]) < 0.00185) {
								return 2;
							} else {
								return 4;
							}
						} else {
							return 0;
						}
					}
				}
			}
		} else {
			if ((input[12]) < 44.9008) {
				if ((input[8]) < 0.00446) {
					if ((input[12]) < 38.4237) {
						return 4;
					} else {
						if ((input[20]) < 323.767) {
							return 4;
						} else {
							if ((input[11]) < 43.7864) {
								if ((input[8]) < 0.004249) {
									if ((input[0]) < 3.41001) {
										return 4;
									} else {
										return 2;
									}
								} else {
									return 0;
								}
							} else {
								return 1;
							}
						}
					}
				} else {
					if ((input[11]) < 25.8763) {
						if ((input[12]) < 38.0125) {
							return 4;
						} else {
							if ((input[11]) < 21.5562) {
								return 2;
							} else {
								return 4;
							}
						}
					} else {
						if ((input[0]) < 4.55339) {
							return 1;
						} else {
							return 2;
						}
					}
				}
			} else {
				if ((input[0]) < 4.70992) {
					if ((input[11]) < 48.6246) {
						if ((input[21]) < 21.9221) {
							if ((input[8]) < 0.006211) {
								if ((input[0]) < 2.91861) {
									if ((input[11]) < 21.3905) {
										return 4;
									} else {
										return 1;
									}
								} else {
									if ((input[12]) < 47.8763) {
										if ((input[20]) < 476.565) {
											if ((input[21]) < 13.0049) {
												return 4;
											} else {
												if ((input[8]) < 0.002292) {
													return 2;
												} else {
													return 1;
												}
											}
										} else {
											return 2;
										}
									} else {
										if ((input[0]) < 3.6226) {
											return 2;
										} else {
											return 1;
										}
									}
								}
							} else {
								return 1;
							}
						} else {
							return 2;
						}
					} else {
						return 1;
					}
				} else {
					return 2;
				}
			}
		}

	}

}
