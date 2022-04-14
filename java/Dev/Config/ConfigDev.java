/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package Dev.Config;

import java.io.File;
import java.util.logging.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import net.sf.l2j.commons.config.ExProperties;
import net.sf.l2j.gameserver.model.holder.IntIntHolder;


/**
 * @author Juvenil Walker
 *
 */


public final class ConfigDev
{
	private static final Logger LOGGER = Logger.getLogger(ConfigDev.class.getName());
	
	
	public static final String VISUAL_FILE = "./config/custom/Visual.properties";
	
	
	// Visual Set //
	public static boolean ENABLE_VISUAL_SET;
	public static boolean ENABLE_VISUAL_EFFECT;
	public static int ID_VISUAL_SOCIAL_ACTION;
	public static int ID_VISUAL_EFFECT;
	public static int VISUAL_TEST_SEGUNDS;
	
	public static int VISUAL_HAIR1;
	public static int VISUAL_CHEST1;
	public static String VISUAL_NAME1;
	
	public static int VISUAL_HAIR2;
	public static int VISUAL_CHEST2;
	public static String VISUAL_NAME2;
	
	public static int VISUAL_HAIR3;
	public static int VISUAL_CHEST3;
	public static int VISUAL_LEGS3;
	public static int VISUAL_GLOVES3;
	public static int VISUAL_BOOTS3;
	public static String VISUAL_NAME3;
	
	public static int VISUAL_HAIR4;
	public static int VISUAL_CHEST4;
	public static int VISUAL_LEGS4;
	public static int VISUAL_GLOVES4;
	public static int VISUAL_BOOTS4;
	public static String VISUAL_NAME4;
	
	public static int VISUAL_HAIR5;
	public static int VISUAL_CHEST5;
	public static int VISUAL_LEGS5;
	public static int VISUAL_GLOVES5;
	public static int VISUAL_BOOTS5;
	public static String VISUAL_NAME5;
	
	public static int VISUAL_HAIR6;
	public static int VISUAL_CHEST6;
	public static int VISUAL_LEGS6;
	public static int VISUAL_GLOVES6;
	public static int VISUAL_BOOTS6;
	public static String VISUAL_NAME6;
	
	public static int VISUAL_HAIR7;
	public static int VISUAL_CHEST7;
	public static int VISUAL_LEGS7;
	public static int VISUAL_GLOVES7;
	public static int VISUAL_BOOTS7;
	public static String VISUAL_NAME7;
	
	public static int VISUAL_HAIR8;
	public static int VISUAL_CHEST8;
	public static int VISUAL_LEGS8;
	public static int VISUAL_GLOVES8;
	public static int VISUAL_BOOTS8;
	public static String VISUAL_NAME8;
	
	public static int VISUAL_HAIR9;
	public static int VISUAL_CHEST9;
	public static int VISUAL_LEGS9;
	public static int VISUAL_GLOVES9;
	public static int VISUAL_BOOTS9;
	public static String VISUAL_NAME9;
	
	public static int VISUAL_HAIR10;
	public static int VISUAL_CHEST10;
	public static int VISUAL_LEGS10;
	public static int VISUAL_GLOVES10;
	public static int VISUAL_BOOTS10;
	public static String VISUAL_NAME10;
	
	public static int VISUAL_HAIR11;
	public static int VISUAL_CHEST11;
	public static int VISUAL_LEGS11;
	public static int VISUAL_GLOVES11;
	public static int VISUAL_BOOTS11;
	public static String VISUAL_NAME11;
	
	public static int VISUAL_HAIR12;
	public static int VISUAL_CHEST12;
	public static int VISUAL_LEGS12;
	public static int VISUAL_GLOVES12;
	public static int VISUAL_BOOTS12;
	public static String VISUAL_NAME12;
	
	public static int VISUAL_HAIR13;
	public static int VISUAL_CHEST13;
	public static int VISUAL_LEGS13;
	public static int VISUAL_GLOVES13;
	public static int VISUAL_BOOTS13;
	public static String VISUAL_NAME13;
	
	public static int VISUAL_HAIR14;
	public static int VISUAL_CHEST14;
	public static int VISUAL_LEGS14;
	public static int VISUAL_GLOVES14;
	public static int VISUAL_BOOTS14;
	public static String VISUAL_NAME14;
	
	public static int VISUAL_HAIR15;
	public static int VISUAL_CHEST15;
	public static int VISUAL_LEGS15;
	public static int VISUAL_GLOVES15;
	public static int VISUAL_BOOTS15;
	public static String VISUAL_NAME15;
	
	public static int VISUAL_HAIR16;
	public static int VISUAL_CHEST16;
	public static int VISUAL_LEGS16;
	public static int VISUAL_GLOVES16;
	public static int VISUAL_BOOTS16;
	public static String VISUAL_NAME16;
	
	public static int VISUAL_HAIR17;
	public static int VISUAL_CHEST17;
	public static int VISUAL_LEGS17;
	public static int VISUAL_GLOVES17;
	public static int VISUAL_BOOTS17;
	public static String VISUAL_NAME17;
	
	public static int VISUAL_HAIR18;
	public static int VISUAL_CHEST18;
	public static int VISUAL_LEGS18;
	public static int VISUAL_GLOVES18;
	public static int VISUAL_BOOTS18;
	public static String VISUAL_NAME18;
	
	public static int VISUAL_HAIR19;
	public static int VISUAL_CHEST19;
	public static int VISUAL_LEGS19;
	public static int VISUAL_GLOVES19;
	public static int VISUAL_BOOTS19;
	public static String VISUAL_NAME19;
	
	public static int VISUAL_HAIR20;
	public static int VISUAL_CHEST20;
	public static int VISUAL_LEGS20;
	public static int VISUAL_GLOVES20;
	public static int VISUAL_BOOTS20;
	public static String VISUAL_NAME20;
	
	// Camera Effect
	public static boolean CAMERA_ENABLED;
	public static int CAMERA_DISTANCE;
	public static int CAMERA_POV;
	public static int CAMERA_ANGLE;
	public static int CAMERA_SPEED;
	public static int CAMERA_DELAY;
	
	
	/**
	 * Loads Visual settings.
	 */
	private static final void loadVisual()
	{
		final ExProperties Visual = initProperties(ConfigDev.VISUAL_FILE);
		
		ENABLE_VISUAL_SET = Visual.getProperty("EnableVisual", true);
		ENABLE_VISUAL_EFFECT = Visual.getProperty("EnableEffect", true);
		ID_VISUAL_SOCIAL_ACTION = Visual.getProperty("SocialActionId", 6842);
		ID_VISUAL_EFFECT = Visual.getProperty("MagicEffectUse", 8001);
		VISUAL_TEST_SEGUNDS = Visual.getProperty("VisualTestTime", 15);
		
		VISUAL_HAIR1 = Visual.getProperty("VisualHair1", 6842);
		VISUAL_CHEST1 = Visual.getProperty("VisualChest1", 30000);
		VISUAL_NAME1 = Visual.getProperty("VisualName1", "Jappan");
		
		VISUAL_HAIR2 = Visual.getProperty("VisualHair2", 6842);
		VISUAL_CHEST2 = Visual.getProperty("VisualChest2", 9400);
		VISUAL_NAME2 = Visual.getProperty("VisualName2", "Jappan");
		
		VISUAL_HAIR3 = Visual.getProperty("VisualHair3", 6842);
		VISUAL_CHEST3 = Visual.getProperty("VisualChest3", 9400);
		VISUAL_LEGS3 = Visual.getProperty("VisualLegs3", 00);
		VISUAL_GLOVES3 = Visual.getProperty("VisualGloves3", 00);
		VISUAL_BOOTS3 = Visual.getProperty("VisualBoots3", 00);
		VISUAL_NAME3 = Visual.getProperty("VisualName3", "Jappan");
		
		VISUAL_HAIR4 = Visual.getProperty("VisualHair4", 6842);
		VISUAL_CHEST4 = Visual.getProperty("VisualChest4", 9400);
		VISUAL_LEGS4 = Visual.getProperty("VisualLegs4", 00);
		VISUAL_GLOVES4 = Visual.getProperty("VisualGloves4", 00);
		VISUAL_BOOTS4 = Visual.getProperty("VisualBoots4", 00);
		VISUAL_NAME4 = Visual.getProperty("VisualName4", "Jappan");
		
		VISUAL_HAIR5 = Visual.getProperty("VisualHair5", 6842);
		VISUAL_CHEST5 = Visual.getProperty("VisualChest5", 9400);
		VISUAL_LEGS5 = Visual.getProperty("VisualLegs5", 00);
		VISUAL_GLOVES5 = Visual.getProperty("VisualGloves5", 00);
		VISUAL_BOOTS5 = Visual.getProperty("VisualBoots5", 00);
		VISUAL_NAME5 = Visual.getProperty("VisualName5", "Jappan");
		
		VISUAL_HAIR6 = Visual.getProperty("VisualHair6", 6842);
		VISUAL_CHEST6 = Visual.getProperty("VisualChest6", 9400);
		VISUAL_LEGS6 = Visual.getProperty("VisualLegs6", 00);
		VISUAL_GLOVES6 = Visual.getProperty("VisualGloves6", 00);
		VISUAL_BOOTS6 = Visual.getProperty("VisualBoots6", 00);
		VISUAL_NAME6 = Visual.getProperty("VisualName6", "Jappan");
		
		VISUAL_HAIR7 = Visual.getProperty("VisualHair7", 6842);
		VISUAL_CHEST7 = Visual.getProperty("VisualChest7", 9400);
		VISUAL_LEGS7 = Visual.getProperty("VisualLegs7", 00);
		VISUAL_GLOVES7 = Visual.getProperty("VisualGloves7", 00);
		VISUAL_BOOTS7 = Visual.getProperty("VisualBoots7", 00);
		VISUAL_NAME7 = Visual.getProperty("VisualName7", "Jappan");
		
		VISUAL_HAIR8 = Visual.getProperty("VisualHair8", 6842);
		VISUAL_CHEST8 = Visual.getProperty("VisualChest8", 9400);
		VISUAL_LEGS8 = Visual.getProperty("VisualLegs8", 00);
		VISUAL_GLOVES8 = Visual.getProperty("VisualGloves8", 00);
		VISUAL_BOOTS8 = Visual.getProperty("VisualBoots8", 00);
		VISUAL_NAME8 = Visual.getProperty("VisualName8", "Jappan");
		
		VISUAL_HAIR9 = Visual.getProperty("VisualHair9", 6842);
		VISUAL_CHEST9 = Visual.getProperty("VisualChest9", 9400);
		VISUAL_LEGS9 = Visual.getProperty("VisualLegs9", 00);
		VISUAL_GLOVES9 = Visual.getProperty("VisualGloves9", 00);
		VISUAL_BOOTS9 = Visual.getProperty("VisualBoots9", 00);
		VISUAL_NAME9 = Visual.getProperty("VisualName9", "Jappan");
		
		VISUAL_HAIR10 = Visual.getProperty("VisualHair10", 6842);
		VISUAL_CHEST10 = Visual.getProperty("VisualChest10", 9400);
		VISUAL_LEGS10 = Visual.getProperty("VisualLegs10", 00);
		VISUAL_GLOVES10 = Visual.getProperty("VisualGloves10", 00);
		VISUAL_BOOTS10 = Visual.getProperty("VisualBoots10", 00);
		VISUAL_NAME10 = Visual.getProperty("VisualName10", "Jappan");
		
		VISUAL_HAIR11 = Visual.getProperty("VisualHair11", 6842);
		VISUAL_CHEST11 = Visual.getProperty("VisualChest11", 9400);
		VISUAL_LEGS11 = Visual.getProperty("VisualLegs11", 00);
		VISUAL_GLOVES11 = Visual.getProperty("VisualGloves11", 00);
		VISUAL_BOOTS11 = Visual.getProperty("VisualBoots11", 00);
		VISUAL_NAME11 = Visual.getProperty("VisualName11", "Jappan");
		
		VISUAL_HAIR12 = Visual.getProperty("VisualHair12", 6842);
		VISUAL_CHEST12 = Visual.getProperty("VisualChest12", 9400);
		VISUAL_LEGS12 = Visual.getProperty("VisualLegs12", 00);
		VISUAL_GLOVES12 = Visual.getProperty("VisualGloves12", 00);
		VISUAL_BOOTS12 = Visual.getProperty("VisualBoots12", 00);
		VISUAL_NAME12 = Visual.getProperty("VisualName12", "Jappan");
		
		VISUAL_HAIR13 = Visual.getProperty("VisualHair13", 6842);
		VISUAL_CHEST13 = Visual.getProperty("VisualChest13", 9400);
		VISUAL_LEGS13 = Visual.getProperty("VisualLegs13", 00);
		VISUAL_GLOVES13 = Visual.getProperty("VisualGloves13", 00);
		VISUAL_BOOTS13 = Visual.getProperty("VisualBoots13", 00);
		VISUAL_NAME13 = Visual.getProperty("VisualName13", "Jappan");
		
		VISUAL_HAIR14 = Visual.getProperty("VisualHair14", 6842);
		VISUAL_CHEST14 = Visual.getProperty("VisualChest14", 9400);
		VISUAL_LEGS14 = Visual.getProperty("VisualLegs14", 00);
		VISUAL_GLOVES14 = Visual.getProperty("VisualGloves14", 00);
		VISUAL_BOOTS14 = Visual.getProperty("VisualBoots14", 00);
		VISUAL_NAME14 = Visual.getProperty("VisualName14", "Jappan");
		
		VISUAL_HAIR15 = Visual.getProperty("VisualHair15", 6842);
		VISUAL_CHEST15 = Visual.getProperty("VisualChest15", 9400);
		VISUAL_LEGS15 = Visual.getProperty("VisualLegs15", 00);
		VISUAL_GLOVES15 = Visual.getProperty("VisualGloves15", 00);
		VISUAL_BOOTS15 = Visual.getProperty("VisualBoots15", 00);
		VISUAL_NAME15 = Visual.getProperty("VisualName15", "Jappan");
		
		VISUAL_HAIR16 = Visual.getProperty("VisualHair16", 6842);
		VISUAL_CHEST16 = Visual.getProperty("VisualChest16", 9400);
		VISUAL_LEGS16 = Visual.getProperty("VisualLegs16", 00);
		VISUAL_GLOVES16 = Visual.getProperty("VisualGloves16", 00);
		VISUAL_BOOTS16 = Visual.getProperty("VisualBoots16", 00);
		VISUAL_NAME16 = Visual.getProperty("VisualName16", "Jappan");
		
		VISUAL_HAIR17 = Visual.getProperty("VisualHair17", 6842);
		VISUAL_CHEST17 = Visual.getProperty("VisualChest17", 9400);
		VISUAL_LEGS17 = Visual.getProperty("VisualLegs17", 00);
		VISUAL_GLOVES17 = Visual.getProperty("VisualGloves17", 00);
		VISUAL_BOOTS17 = Visual.getProperty("VisualBoots17", 00);
		VISUAL_NAME17 = Visual.getProperty("VisualName17", "Jappan");
		
		VISUAL_HAIR18 = Visual.getProperty("VisualHair18", 6842);
		VISUAL_CHEST18 = Visual.getProperty("VisualChest18", 9400);
		VISUAL_LEGS18 = Visual.getProperty("VisualLegs18", 00);
		VISUAL_GLOVES18 = Visual.getProperty("VisualGloves18", 00);
		VISUAL_BOOTS18 = Visual.getProperty("VisualBoots18", 00);
		VISUAL_NAME18 = Visual.getProperty("VisualName18", "Jappan");
		
		VISUAL_HAIR19 = Visual.getProperty("VisualHair19", 6842);
		VISUAL_CHEST19 = Visual.getProperty("VisualChest19", 9400);
		VISUAL_LEGS19 = Visual.getProperty("VisualLegs19", 00);
		VISUAL_GLOVES19 = Visual.getProperty("VisualGloves19", 00);
		VISUAL_BOOTS19 = Visual.getProperty("VisualBoots19", 00);
		VISUAL_NAME19 = Visual.getProperty("VisualName19", "Jappan");
		
		VISUAL_HAIR20 = Visual.getProperty("VisualHair20", 6842);
		VISUAL_CHEST20 = Visual.getProperty("VisualChest20", 9400);
		VISUAL_LEGS20 = Visual.getProperty("VisualLegs20", 00);
		VISUAL_GLOVES20 = Visual.getProperty("VisualGloves20", 00);
		VISUAL_BOOTS20 = Visual.getProperty("VisualBoots20", 00);
		VISUAL_NAME20 = Visual.getProperty("VisualName20", "Jappan");
		
		CAMERA_ENABLED = Visual.getProperty("CameraEnabled", true);
		CAMERA_DISTANCE = Visual.getProperty("CameraDistance", 10);
		CAMERA_POV = Visual.getProperty("CameraPOV", 10);
		CAMERA_ANGLE = Visual.getProperty("CameraAngle", 10);
		CAMERA_SPEED = Visual.getProperty("CameraSpeed", 10);
		CAMERA_DELAY = Visual.getProperty("CameraDuration", 10);
	}
	/**
	 * Initialize {@link ExProperties} from specified configuration file.
	 * @param filename : File name to be loaded.
	 * @return ExProperties : Initialized {@link ExProperties}.
	 */
	public static final ExProperties initProperties(String filename)
	{
		final ExProperties result = new ExProperties();
		
		try
		{
			result.load(new File(filename));
		}
		catch (Exception e)
		{
			LOGGER.info("An error occured loading '{}' config."+ e + filename);
		}
		
		return result;
	}
	
	@SuppressWarnings("unused")
	private static final int[][] parseItemsList(String line)
	{
		final String[] propertySplit = line.split(";");
		if (propertySplit.length == 0)
			return null;
		
		int i = 0;
		String[] valueSplit;
		final int[][] result = new int[propertySplit.length][];
		for (String value : propertySplit)
		{
			valueSplit = value.split(",");
			if (valueSplit.length != 2)
			{
				LOGGER.info("Config: Error parsing entry -> \"" + valueSplit[0] + "\", should be itemId,itemNumber");
				return null;
			}
			
			result[i] = new int[2];
			try
			{
				result[i][0] = Integer.parseInt(valueSplit[0]);
			}
			catch (NumberFormatException e)
			{
				LOGGER.info("Config: Error parsing item ID -> \"" + valueSplit[0] + "\"");
				return null;
			}
			
			try
			{
				result[i][1] = Integer.parseInt(valueSplit[1]);
			}
			catch (NumberFormatException e)
			{
				LOGGER.info("Config: Error parsing item amount -> \"" + valueSplit[1] + "\"");
				return null;
			}
			i++;
		}
		return result;
	}
	
	public static final class ClassMasterSettings
	{
		private final Map<Integer, Boolean> _allowedClassChange;
		private final Map<Integer, List<IntIntHolder>> _claimItems;
		private final Map<Integer, List<IntIntHolder>> _rewardItems;
		
		public ClassMasterSettings(String configLine)
		{
			_allowedClassChange = new HashMap<>(3);
			_claimItems = new HashMap<>(3);
			_rewardItems = new HashMap<>(3);
			
			if (configLine != null)
				parseConfigLine(configLine.trim());
		}
		
		private void parseConfigLine(String configLine)
		{
			StringTokenizer st = new StringTokenizer(configLine, ";");
			while (st.hasMoreTokens())
			{
				// Get allowed class change.
				int job = Integer.parseInt(st.nextToken());
				
				_allowedClassChange.put(job, true);
				
				List<IntIntHolder> items = new ArrayList<>();
				
				// Parse items needed for class change.
				if (st.hasMoreTokens())
				{
					StringTokenizer st2 = new StringTokenizer(st.nextToken(), "[],");
					while (st2.hasMoreTokens())
					{
						StringTokenizer st3 = new StringTokenizer(st2.nextToken(), "()");
						items.add(new IntIntHolder(Integer.parseInt(st3.nextToken()), Integer.parseInt(st3.nextToken())));
					}
				}
				
				// Feed the map, and clean the list.
				_claimItems.put(job, items);
				items = new ArrayList<>();
				
				// Parse gifts after class change.
				if (st.hasMoreTokens())
				{
					StringTokenizer st2 = new StringTokenizer(st.nextToken(), "[],");
					while (st2.hasMoreTokens())
					{
						StringTokenizer st3 = new StringTokenizer(st2.nextToken(), "()");
						items.add(new IntIntHolder(Integer.parseInt(st3.nextToken()), Integer.parseInt(st3.nextToken())));
					}
				}
				
				_rewardItems.put(job, items);
			}
		}
		
		public boolean isAllowed(int job)
		{
			if (_allowedClassChange == null)
				return false;
			
			if (_allowedClassChange.containsKey(job))
				return _allowedClassChange.get(job);
			
			return false;
		}
		
		public List<IntIntHolder> getRewardItems(int job)
		{
			return _rewardItems.get(job);
		}
		
		public List<IntIntHolder> getRequiredItems(int job)
		{
			return _claimItems.get(job);
		}
	}
	
	
	public static final void loadGameServer()
	{
		LOGGER.info("Loading gameserver configuration files.");
		
		// Visual settings
		loadVisual();
		
	}

}
