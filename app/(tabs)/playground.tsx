
import React, { useEffect, useState, useRef, useCallback, useMemo } from 'react';
import { Button, Searchbar, Surface, Avatar, Chip, Divider, Title, Caption, Paragraph, useTheme, IconButton, Card } from 'react-native-paper';
import { AppHeader } from '../components/core/AppHeader';
import { DUMMY_POSTS } from '../utils/constants';
import { Post } from '../utils/types';
import { ScrollView, View, Text, Linking, Animated, useWindowDimensions, Pressable } from 'react-native';
import { Image } from 'expo-image';
import { useVideoPlayer, VideoView } from 'expo-video';
import { useEvent } from 'expo';
import type { MediaItem } from '../utils/types';

type CollaborationMedia = Extract<MediaItem, { type: 'collaboration' }>;
type ChallengeMedia = Extract<MediaItem, { type: 'challenge' }>;


export default function PlaygroundsScreen() {
   const [searchQuery, setSearchQuery] = React.useState('');
   const theme = useTheme();

   useEffect(() => {
   }, []);

   return (
      <View style={{ flex: 1, backgroundColor: theme.colors.background }}>
         <Searchbar
            placeholder="Search"
            onChangeText={setSearchQuery}
            value={searchQuery}
            style={{ margin: 16 }}
            iconColor={theme.colors.primary}
            inputStyle={{ fontSize: 16, color: theme.colors.onBackground }}
         />
         <Surface style={{ margin: 16, padding: 16, flex: 1, backgroundColor: theme.colors.surface }}>
            <Posts searchQuery={searchQuery} />
         </Surface>
      </View>
   );
}

function Posts({ searchQuery }: { searchQuery: string }) {
   const [posts, setPosts] = useState<Post[]>(DUMMY_POSTS);
   const [userVotes, setUserVotes] = useState<Record<string, number>>({});
   const theme = useTheme();

   const handleVote = (postId: string, newVote: -1 | 0 | 1) => {
      setUserVotes(prevUV => {
         const oldVote = prevUV[postId] ?? 0;
         setPosts(prevPosts => prevPosts.map(p => {
            if (p.id !== postId) return p;
            const votes = (p.votes ?? 0) + (newVote - oldVote);
            return { ...p, votes };
         }));
         return { ...prevUV, [postId]: newVote };
      });
   };

   useEffect(() => {
      if (searchQuery) {
         const q = searchQuery.toLowerCase();
         const filteredPosts = DUMMY_POSTS.filter(post =>
            (post.title || '').toLowerCase().includes(q) ||
            (post.content || '').toLowerCase().includes(q)
         );
         setPosts(filteredPosts);
      } else {
         setPosts(DUMMY_POSTS);
      }
   }, [searchQuery]);

   return (
      <ScrollView
         style={{ marginBottom: 16, flex: 1 }}
         contentContainerStyle={{ paddingBottom: 16 }}
         keyboardShouldPersistTaps="handled"
      >
         {posts.map(post => (
            <Card key={post.id} style={{ marginBottom: 12, borderRadius: 8, elevation: 2 }}>
               <View style={{ flexDirection: 'row', alignItems: 'flex-start', padding: 12 }}>
                  <VoteControls
                     postId={post.id}
                     votes={post.votes ?? 0}
                     userVote={userVotes[post.id] ?? 0}
                     onVote={handleVote}
                     vertical
                  />

                  <View style={{ flex: 1, marginLeft: 8 }}>
                     <Title style={{ marginBottom: 4, color: theme.colors.primary }}>{post.title}</Title>
                     <Caption>{post.author ?? 'Unknown'} · {post.community}</Caption>
                  </View>
               </View>

               <Card.Content style={{ backgroundColor: theme.colors.surface }}>
                  {post.content && <Paragraph style={{ marginBottom: 8 }}>{post.content}</Paragraph>}

                  {post.media && post.media.length > 0 && (
                     <View style={{ marginTop: 8 }}>
                        {post.media.map((mediaItem, index) => (
                           <View key={index} style={{ marginBottom: 12 }}>
                              <MediaItemRenderer mediaItem={mediaItem} />
                              {'caption' in mediaItem && mediaItem.caption && <Caption style={{ marginTop: 4 }}>{mediaItem.caption}</Caption>}
                           </View>
                        ))}
                     </View>
                  )}
               </Card.Content>
            </Card>
         ))}

         {posts.length === 0 && (
            <View style={{ padding: 24, alignItems: 'center' }}>
               <Text>No posts found.</Text>
            </View>
         )}
      </ScrollView>
   );

}


const VoteControls: React.FC<{ postId: string; votes: number; userVote: number; onVote: (postId: string, vote: -1 | 0 | 1) => void; vertical?: boolean }> = React.memo(({ postId, votes, userVote, onVote, vertical }) => {
   const theme = useTheme();
   const scale = useRef(new Animated.Value(1)).current;
   const prev = useRef(votes);

   useEffect(() => {
      if (prev.current !== votes) {
         Animated.sequence([
            Animated.timing(scale, { toValue: 1.15, duration: 120, useNativeDriver: true }),
            Animated.timing(scale, { toValue: 1, duration: 120, useNativeDriver: true })
         ]).start();
         prev.current = votes;
      }
   }, [votes, scale]);

   const isVertical = !!vertical;
   const handleUp = useCallback(() => onVote(postId, userVote === 1 ? 0 : 1), [onVote, postId, userVote]);
   const handleDown = useCallback(() => onVote(postId, userVote === -1 ? 0 : -1), [onVote, postId, userVote]);

   if (isVertical) {
      return (
         <View style={{ width: 56, alignItems: 'center' }}>
            <IconButton
               icon="thumb-up"
               size={28}
               onPress={handleUp}
               iconColor={userVote === 1 ? '#fff' : theme.colors.onBackground}
               containerColor={userVote === 1 ? theme.colors.primary : 'transparent'}
               accessibilityLabel="Upvote"
            />
            <Animated.Text style={{ transform: [{ scale }], fontWeight: 'bold', color: theme.colors.onBackground }}>{votes}</Animated.Text>
            <IconButton
               icon="thumb-down"
               size={28}
               onPress={handleDown}
               iconColor={userVote === -1 ? '#fff' : theme.colors.onBackground}
               containerColor={userVote === -1 ? theme.colors.primary : 'transparent'}
               accessibilityLabel="Downvote"
            />
         </View>
      );
   }

   return (
      <View style={{ flexDirection: 'row', alignItems: 'center' }}>
         <IconButton
            icon="thumb-up"
            size={26}
            onPress={handleUp}
            iconColor={userVote === 1 ? '#fff' : theme.colors.onBackground}
            containerColor={userVote === 1 ? theme.colors.primary : 'transparent'}
            accessibilityLabel="Upvote"
         />
         <Animated.Text style={{ transform: [{ scale }], marginHorizontal: 6, fontWeight: 'bold', color: theme.colors.onBackground }}>{votes}</Animated.Text>
         <IconButton
            icon="thumb-down"
            size={26}
            onPress={handleDown}
            iconColor={userVote === -1 ? '#fff' : theme.colors.onBackground}
            containerColor={userVote === -1 ? theme.colors.primary : 'transparent'}
            accessibilityLabel="Downvote"
         />
      </View>
   );
}, (p, n) => p.votes === n.votes && p.userVote === n.userVote && p.vertical === n.vertical);

const MediaVideo = React.memo(({ url }: { url: string | number }) => {
   const source: any = typeof url === 'number' ? url : { uri: url };
   const player = useVideoPlayer(source, player => {
      player.loop = true;
      player.play();
   });

   // keep event subscription for future use (e.g., UI state)
   useEvent(player, 'playingChange');

   return (
      <VideoView player={player} allowsFullscreen allowsPictureInPicture style={{ width: '100%', height: 200 }} />
   );
}, (p, n) => p.url === n.url);

const MediaImage = React.memo(({ url }: { url: string }) => {
   const style = useMemo(() => ({ height: 200, alignSelf: 'stretch' as const }), []);
   return <Image source={{ uri: url }} style={style} contentFit="cover" />;
}, (p, n) => p.url === n.url);

const MediaText = React.memo(({ text }: { text: string }) => {
   return <Text style={{ marginTop: 4 }}>{text}</Text>;
}, (p, n) => p.text === n.text);

const MediaPoll = React.memo(({ question, options }: { question: string; options: string[] }) => {
   const theme = useTheme();
   const renderOption = useCallback((option: string, index: number) => (
      <Button key={index} mode="outlined" style={{ marginTop: 4 }} color={theme.colors.primary}>{option}</Button>
   ), [theme.colors.primary]);
   return (
      <View style={{ marginTop: 4 }}>
         <Text>{question}</Text>
         {options.map(renderOption)}
      </View>
   );
}, (p, n) => p.question === n.question && p.options === n.options);

const MediaQuote = React.memo(({ quote, author }: { quote: string; author: string }) => {
   return (
      <View style={{ marginTop: 4, padding: 12, backgroundColor: '#f0f0f0', borderRadius: 8 }}>
         <Text style={{ fontStyle: 'italic' }}>&quot;{quote}&quot;</Text>
         {author && <Text style={{ marginTop: 4, textAlign: 'right', fontWeight: 'bold' }}>- {author}</Text>}
      </View>
   );
}, (p, n) => p.quote === n.quote && p.author === n.author);

const MediaCode = React.memo(({ code, language }: { code: string; language?: string }) => {
   return (
      <View style={{ marginTop: 4, padding: 12, backgroundColor: '#e0e0e0', borderRadius: 8 }}>
         {language && <Text style={{ fontWeight: 'bold', marginBottom: 4 }}>{language}</Text>}
         <Text style={{ fontFamily: 'monospace' }}>{code}</Text>
      </View>
   );
}, (p, n) => p.code === n.code && p.language === n.language);

const MediaFile = React.memo(({ url, caption }: { url?: string; caption?: string }) => {
   const theme = useTheme();
   const open = useCallback(() => url && Linking.openURL(url), [url]);
   if (!url) return null;
   return (
      <View style={{ marginTop: 4 }}>
         {caption && <Text>{caption}</Text>}
         <Button mode="outlined" onPress={open} style={{ marginTop: 8 }} color={theme.colors.primary}>Open file</Button>
      </View>
   );
}, (p, n) => p.url === n.url && p.caption === n.caption);

const MediaAudio = React.memo(({ url, caption }: { url?: string; caption?: string }) => {
   const theme = useTheme();
   const open = useCallback(() => url && Linking.openURL(url), [url]);
   if (!url) return null;
   return (
      <View style={{ marginTop: 4 }}>
         {caption && <Text>{caption}</Text>}
         <Button mode="outlined" onPress={open} style={{ marginTop: 8 }} color={theme.colors.primary}>Play audio</Button>
      </View>
   );
}, (p, n) => p.url === n.url && p.caption === n.caption);

const MediaEvent = React.memo(({ title, date, description }: { title?: string; date?: string; description?: string }) => {
   return (
      <View style={{ marginTop: 4 }}>
         {title && <Text style={{ fontWeight: 'bold' }}>{title}</Text>}
         {date && <Text>{date}</Text>}
         {description && <Text style={{ marginTop: 4 }}>{description}</Text>}
      </View>
   );
}, (p, n) => p.title === n.title && p.date === n.date && p.description === n.description);

const MediaLocation = React.memo(({ latitude, longitude, caption }: { latitude?: number; longitude?: number; caption?: string }) => {
   const theme = useTheme();
   const open = useCallback(() => {
      if (latitude == null || longitude == null) return;
      const url = `https://www.google.com/maps/search/?api=1&query=${latitude},${longitude}`;
      Linking.openURL(url);
   }, [latitude, longitude]);
   if (latitude == null || longitude == null) return null;
   return (
      <View style={{ marginTop: 4 }}>
         {caption && <Text>{caption}</Text>}
         <Button mode="outlined" onPress={open} style={{ marginTop: 8 }} color={theme.colors.primary}>Open map</Button>
      </View>
   );
}, (p, n) => p.latitude === n.latitude && p.longitude === n.longitude && p.caption === n.caption);

const MediaProduct = React.memo(({ name, price, url, caption }: { name?: string; price?: number; url?: string; caption?: string }) => {
   const theme = useTheme();
   const open = useCallback(() => url && Linking.openURL(url), [url]);
   return (
      <View style={{ marginTop: 4 }}>
         {name && <Text style={{ fontWeight: 'bold' }}>{name}</Text>}
         {typeof price === 'number' && <Text>{`$${price}`}</Text>}
         {caption && <Text style={{ marginTop: 4 }}>{caption}</Text>}
         {url && <Button mode="outlined" onPress={open} style={{ marginTop: 8 }} color={theme.colors.primary}>View product</Button>}
      </View>
   );
}, (p, n) => p.name === n.name && p.price === n.price && p.url === n.url && p.caption === n.caption);

const MediaProfile = React.memo(({ username, displayName, avatarUrl, caption }: { username?: string; displayName?: string; avatarUrl?: string; caption?: string }) => {
   return (
      <View style={{ marginTop: 4 }}>
         <Text style={{ fontWeight: 'bold' }}>{displayName || username}</Text>
         {caption && <Text>{caption}</Text>}
      </View>
   );
}, (p, n) => p.username === n.username && p.displayName === n.displayName && p.avatarUrl === n.avatarUrl && p.caption === n.caption);

const MediaAnnouncement = React.memo(({ title, content }: { title?: string; content?: string }) => {
   return (
      <View style={{ marginTop: 4, padding: 8, backgroundColor: '#fff3cd', borderRadius: 6 }}>
         {title && <Text style={{ fontWeight: 'bold' }}>{title}</Text>}
         {content && <Text style={{ marginTop: 4 }}>{content}</Text>}
      </View>
   );
}, (p, n) => p.title === n.title && p.content === n.content);

const MediaBadge = React.memo(({ name, description, imageUrl }: { name?: string; description?: string; imageUrl?: string }) => {
   return (
      <View style={{ marginTop: 4 }}>
         {name && <Text style={{ fontWeight: 'bold' }}>{name}</Text>}
         {description && <Text>{description}</Text>}
      </View>
   );
}, (p, n) => p.name === n.name && p.description === n.description && p.imageUrl === n.imageUrl);

const MediaSimpleList = React.memo(({ title, items }: { title?: string; items?: string[] }) => {
   return (
      <View style={{ marginTop: 4 }}>
         {title && <Text style={{ fontWeight: 'bold', marginBottom: 4 }}>{title}</Text>}
         {items?.map((it, i) => <Text key={i}>• {it}</Text>)}
      </View>
   );
}, (p, n) => p.title === n.title && p.items === n.items);

const MediaInfographic = React.memo(({ url, caption }: { url?: string; caption?: string }) => {
   if (!url) return null;
   return (
      <View style={{ marginTop: 4 }}>
         {caption && <Text>{caption}</Text>}
         <Image source={{ uri: url }} style={{ width: '100%', height: 200, marginTop: 8 }} contentFit="contain" />
      </View>
   );
}, (p, n) => p.url === n.url && p.caption === n.caption);

const MediaTable = React.memo(({ headers, rows }: { headers?: string[]; rows?: (string | number)[][] }) => {
   return (
      <View style={{ marginTop: 4 }}>
         {headers && <Text style={{ fontWeight: 'bold' }}>{headers.join(' | ')}</Text>}
         {rows?.map((r, i) => <Text key={i}>{r.join(' | ')}</Text>)}
      </View>
   );
}, (p, n) => p.headers === n.headers && p.rows === n.rows);

const MediaSlideshow = React.memo(function MediaSlideshow({ slides, autoplay = true, intervalMs = 3500 }: { slides?: { url?: string; caption?: string }[]; autoplay?: boolean; intervalMs?: number }) {
   const windowWidth = useWindowDimensions().width;
   const theme = useTheme();
   const [index, setIndex] = useState(0);
   const scrollX = React.useRef(new Animated.Value(0)).current;
   const scrollRef = useRef<ScrollView | null>(null);
   const timerRef = useRef<ReturnType<typeof setTimeout> | null>(null);
   const [paused, setPaused] = useState(false);
   const [containerWidth, setContainerWidth] = useState<number>(windowWidth);
   const indexRef = useRef<number>(index);

   useEffect(() => { indexRef.current = index; }, [index]);

   const stopAutoplay = useCallback(() => {
      if (timerRef.current) {
         clearTimeout(timerRef.current);
         timerRef.current = null;
      }
   }, []);

   const scheduleNext = useCallback(() => {
      if (timerRef.current) return;
      timerRef.current = setTimeout(() => {
         timerRef.current = null;
         if (paused || !autoplay || (slides?.length || 0) <= 1) return;
         const next = (indexRef.current + 1) % (slides?.length || 1);
         const pageW = containerWidth || windowWidth;
         if (scrollRef.current && typeof scrollRef.current.scrollTo === 'function') {
            scrollRef.current.scrollTo({ x: next * pageW, animated: true });
         }
         // schedule another
         scheduleNext();
      }, intervalMs);
   }, [autoplay, paused, slides?.length, intervalMs, containerWidth, windowWidth]);

   const startAutoplay = useCallback(() => {
      stopAutoplay();
      if (!autoplay || paused || (slides?.length || 0) <= 1) return;
      scheduleNext();
   }, [autoplay, paused, slides?.length, scheduleNext, stopAutoplay]);

   const togglePause = useCallback(() => {
      if (paused) {
         setPaused(false);
         startAutoplay();
      } else {
         setPaused(true);
         stopAutoplay();
      }
   }, [paused, startAutoplay, stopAutoplay]);

   useEffect(() => {
      startAutoplay();
      return () => stopAutoplay();
   }, [startAutoplay, stopAutoplay]);

   if (!slides || slides.length === 0) return null;

   return (
      <View style={{ marginTop: 4 }} onLayout={e => setContainerWidth(e.nativeEvent.layout.width)}>
         <Animated.ScrollView
            ref={scrollRef}
            horizontal
            pagingEnabled
            showsHorizontalScrollIndicator={false}
            removeClippedSubviews
            decelerationRate="fast"
            onScroll={Animated.event(
               [{ nativeEvent: { contentOffset: { x: scrollX } } }],
               { useNativeDriver: true }
            )}
            onMomentumScrollEnd={e => setIndex(Math.round(e.nativeEvent.contentOffset.x / (containerWidth || windowWidth)))}
            onScrollBeginDrag={() => stopAutoplay()}
            onScrollEndDrag={() => startAutoplay()}
            scrollEventThrottle={16}
         >
            {slides.map((s, i) => {
               const visible = Math.abs(i - index) <= 1;
               return (
                  <Pressable key={i} onPress={() => togglePause()} accessibilityRole="button">
                     <View style={{ width: containerWidth, alignItems: 'center', justifyContent: 'center' }}>
                        {visible ? (
                           s.url && <Image source={{ uri: s.url }} style={{ width: (containerWidth || windowWidth) - 32, height: 180, borderRadius: 8, marginHorizontal: 16 }} contentFit="cover" />
                        ) : (
                           <View style={{ width: (containerWidth || windowWidth) - 32, height: 180, borderRadius: 8, marginHorizontal: 16, backgroundColor: '#f6f6f6' }} />
                        )}
                        {s.caption && <Caption style={{ marginTop: 6 }}>{s.caption}</Caption>}
                     </View>
                  </Pressable>
               );
            })}
         </Animated.ScrollView>

         <View style={{ flexDirection: 'row', justifyContent: 'center', marginTop: 8 }}>
            {slides.map((_, i) => {
               const pageW = containerWidth || windowWidth;
               const inputRange = [(i - 1) * pageW, i * pageW, (i + 1) * pageW];
               const scale = scrollX.interpolate({ inputRange, outputRange: [1, 1.6, 1], extrapolate: 'clamp' });
               const opacity = scrollX.interpolate({ inputRange, outputRange: [0.6, 1, 0.6], extrapolate: 'clamp' });
               return (
                  <Pressable key={i} onPress={() => {
                     // jump to tapped slide and resume autoplay
                     const pageW2 = containerWidth || windowWidth;
                     if (scrollRef.current && typeof scrollRef.current.scrollTo === 'function') {
                        scrollRef.current.scrollTo({ x: i * pageW, animated: true });
                     }
                     setIndex(i);
                     setPaused(false);
                     startAutoplay();
                  }} accessibilityRole="button">
                     <Animated.View style={{ width: 8, height: 8, borderRadius: 4, margin: 4, backgroundColor: theme.colors.primary, transform: [{ scale }], opacity }} />
                  </Pressable>
               );
            })}
         </View>
      </View>
   );
}, (p, n) => p.slides === n.slides && p.autoplay === n.autoplay && p.intervalMs === n.intervalMs);

const MediaGallery = React.memo(function MediaGallery({ images, autoplay = true, intervalMs = 3500 }: { images?: { url?: string; caption?: string }[]; autoplay?: boolean; intervalMs?: number }) {
   const windowWidth = useWindowDimensions().width;
   const theme = useTheme();
   const [index, setIndex] = useState(0);
   const scrollX = React.useRef(new Animated.Value(0)).current;
   const scrollRef = useRef<ScrollView | null>(null);
   const timerRef = useRef<ReturnType<typeof setTimeout> | null>(null);

   const [paused, setPaused] = useState(false);
   const [containerWidth, setContainerWidth] = useState<number>(windowWidth);
   const indexRef = useRef<number>(index);

   useEffect(() => { indexRef.current = index; }, [index]);

   const stopAutoplay = useCallback(() => {
      if (timerRef.current) {
         clearTimeout(timerRef.current);
         timerRef.current = null;
      }
   }, []);

   const scheduleNext = useCallback(() => {
      if (timerRef.current) return;
      timerRef.current = setTimeout(() => {
         timerRef.current = null;
         if (paused || !autoplay || (images?.length || 0) <= 1) return;
         const next = (indexRef.current + 1) % (images?.length || 1);
         const pageW = containerWidth || windowWidth;
         if (scrollRef.current && typeof scrollRef.current.scrollTo === 'function') {
            scrollRef.current.scrollTo({ x: next * pageW, animated: true });
         }
         scheduleNext();
      }, intervalMs);
   }, [autoplay, paused, images?.length, intervalMs, containerWidth, windowWidth]);

   const startAutoplay = useCallback(() => {
      stopAutoplay();
      if (!autoplay || paused || (images?.length || 0) <= 1) return;
      scheduleNext();
   }, [autoplay, paused, images?.length, scheduleNext, stopAutoplay]);

   const togglePause = useCallback(() => {
      if (paused) {
         setPaused(false);
         startAutoplay();
      } else {
         setPaused(true);
         stopAutoplay();
      }
   }, [paused, startAutoplay, stopAutoplay]);

   useEffect(() => {
      startAutoplay();
      return () => stopAutoplay();
   }, [startAutoplay, stopAutoplay]);

   if (!images || images.length === 0) return null;

   return (
      <View style={{ marginTop: 4 }} onLayout={e => setContainerWidth(e.nativeEvent.layout.width)}>
         <Animated.ScrollView
            ref={scrollRef}
            horizontal
            pagingEnabled
            showsHorizontalScrollIndicator={false}
            removeClippedSubviews
            decelerationRate="fast"
            onScroll={Animated.event(
               [{ nativeEvent: { contentOffset: { x: scrollX } } }],
               { useNativeDriver: true }
            )}
            onMomentumScrollEnd={e => setIndex(Math.round(e.nativeEvent.contentOffset.x / (containerWidth || windowWidth)))}
            onScrollBeginDrag={() => stopAutoplay()}
            onScrollEndDrag={() => startAutoplay()}
            scrollEventThrottle={16}
         >
            {images.map((img, i) => {
               const visible = Math.abs(i - index) <= 1;
               return (
                  <Pressable key={i} onPress={() => togglePause()} accessibilityRole="button">
                     <View style={{ width: containerWidth, alignItems: 'center', justifyContent: 'center' }}>
                        {visible ? (
                           img.url && <Image source={{ uri: img.url }} style={{ width: (containerWidth || windowWidth) - 48, height: 150, borderRadius: 8, marginHorizontal: 16 }} contentFit="cover" />
                        ) : (
                           <View style={{ width: (containerWidth || windowWidth) - 48, height: 150, borderRadius: 8, marginHorizontal: 16, backgroundColor: '#f6f6f6' }} />
                        )}
                        {img.caption && <Caption style={{ marginTop: 6 }}>{img.caption}</Caption>}
                     </View>
                  </Pressable>
               );
            })}
         </Animated.ScrollView>

         <View style={{ flexDirection: 'row', justifyContent: 'center', marginTop: 8 }}>
            {images.map((_, i) => {
               const pageW = containerWidth || windowWidth;
               const inputRange = [(i - 1) * pageW, i * pageW, (i + 1) * pageW];
               const scale = scrollX.interpolate({ inputRange, outputRange: [1, 1.6, 1], extrapolate: 'clamp' });
               const opacity = scrollX.interpolate({ inputRange, outputRange: [0.6, 1, 0.6], extrapolate: 'clamp' });
               return (
                  <Pressable key={i} onPress={() => {
                     const pageW2 = containerWidth || windowWidth;
                     if (scrollRef.current && typeof scrollRef.current.scrollTo === 'function') {
                        scrollRef.current.scrollTo({ x: i * pageW2, animated: true });
                     }
                     setIndex(i);
                     setPaused(false);
                     startAutoplay();
                  }} accessibilityRole="button">
                     <Animated.View style={{ width: 8, height: 8, borderRadius: 4, margin: 4, backgroundColor: theme.colors.primary, transform: [{ scale }], opacity }} />
                  </Pressable>
               );
            })}
         </View>
      </View>
   );
}, (p, n) => p.images === n.images && p.autoplay === n.autoplay && p.intervalMs === n.intervalMs);


const MediaFAQ = React.memo(({ faqs }: { faqs?: { question: string; answer: string }[] }) => {
   return (
      <View style={{ marginTop: 4 }}>
         {faqs?.map((f, i) => (
            <View key={i} style={{ marginBottom: 6 }}>
               <Text style={{ fontWeight: 'bold' }}>{f.question}</Text>
               <Text>{f.answer}</Text>
            </View>
         ))}
      </View>
   );
}, (p, n) => p.faqs === n.faqs);

const MediaLink = React.memo(({ url, caption }: { url?: string; caption?: string }) => {
   const theme = useTheme();
   const open = useCallback(() => url && Linking.openURL(url), [url]);
   if (!url) return null;
   return (
      <View style={{ marginTop: 4 }}>
         {caption && <Text>{caption}</Text>}
         <Button mode="outlined" onPress={open} style={{ marginTop: 8 }} color={theme.colors.primary}>Open link</Button>
      </View>
   );
}, (p, n) => p.url === n.url && p.caption === n.caption);

const MediaItemRenderer = React.memo(({ mediaItem }: { mediaItem: MediaItem }) => {
   switch (mediaItem.type) {
      case 'image':
         return <MediaImage url={mediaItem.url} />;
      case 'video':
         return <MediaVideo url={mediaItem.url} />;
      case 'text':
         return <MediaText text={mediaItem.text || ''} />;
      case 'poll':
      case 'survey':
         return <MediaPoll question={mediaItem.question} options={mediaItem.options} />;
      case 'quote':
         return <MediaQuote quote={mediaItem.text} author={mediaItem.author || ''} />;
      case 'code':
         return <MediaCode code={mediaItem.code || ''} language={mediaItem.language} />;
      case 'file':
         return <MediaFile url={mediaItem.url} caption={mediaItem.caption} />;
      case 'audio':
         return <MediaAudio url={mediaItem.url} caption={mediaItem.caption} />;
      case 'event':
         return <MediaEvent title={mediaItem.title} date={mediaItem.date} description={mediaItem.description} />;
      case 'location':
      case 'map':
         return <MediaLocation latitude={mediaItem.latitude} longitude={mediaItem.longitude} caption={mediaItem.caption} />;
      case 'product':
         return <MediaProduct name={mediaItem.name} price={mediaItem.price} url={mediaItem.url} caption={mediaItem.caption} />;
      case 'profile':
         return <MediaProfile username={mediaItem.username} displayName={mediaItem.displayName} avatarUrl={mediaItem.avatarUrl} caption={mediaItem.caption} />;
      case 'announcement':
         return <MediaAnnouncement title={mediaItem.title} content={mediaItem.content} />;
      case 'badge':
      case 'achievement':
      case 'milestone':
         return <MediaBadge name={mediaItem.name} description={mediaItem.description} imageUrl={mediaItem.imageUrl} />;
      case 'testimonial':
         return <MediaQuote quote={mediaItem.content} author={mediaItem.author || ''} />;
      case 'caseStudy':
         return <MediaSimpleList title={mediaItem.title} items={[mediaItem.content || '']} />;
      case 'infographic':
         return <MediaInfographic url={mediaItem.url} caption={mediaItem.caption} />;
      case 'timeline':
         return <MediaSimpleList title={mediaItem.caption} items={mediaItem.events?.map((e) => `${e.date}: ${e.event}`) || []} />;
      case 'chart':
         return <MediaSimpleList title={mediaItem.caption} items={[mediaItem.chartType || '', JSON.stringify(mediaItem.data || {})]} />;
      case 'table':
         return <MediaTable headers={mediaItem.headers} rows={mediaItem.rows as string[][]} />;
      case 'slideshow':
         return <MediaSlideshow slides={mediaItem.slides} />;
      case 'animation':
      case 'virtualTour':
      case '3dModel':
         return <MediaLink url={mediaItem.url} caption={mediaItem.caption} />;
      case 'webinar':
      case 'workshop':
         return <MediaEvent title={mediaItem.title} date={mediaItem.date} description={mediaItem.description} />;
      case 'ebook':
      case 'researchPaper':
         return <MediaLink url={mediaItem.url} caption={mediaItem.title} />;
      case 'newsletter':
         return <MediaLink url={mediaItem.url} caption={mediaItem.title} />;
      case 'faq':
         return <MediaFAQ faqs={mediaItem.faqs} />;
      case 'gallery':
         return <MediaGallery images={mediaItem.images} />;
      case 'link':
         return <MediaLink url={mediaItem.url} caption={mediaItem.caption} />;
      case 'summary':
         return <MediaText text={mediaItem.text || ''} />;
      case 'reflection':
         return <MediaSimpleList title={mediaItem.prompt} items={[mediaItem.response || '']} />;
      case 'challenge':
      case 'collaboration':
      case 'sponsorship': {
         const title = 'title' in mediaItem ? mediaItem.title : ('projectTitle' in mediaItem ? (mediaItem as CollaborationMedia).projectTitle || '' : '');
         const rolesOrRules = 'roles' in mediaItem ? (mediaItem as CollaborationMedia).roles || '' : ('rules' in mediaItem ? (mediaItem as ChallengeMedia).rules || '' : '');
         return <MediaSimpleList title={title} items={[mediaItem.description || rolesOrRules || ''] } />;
      }
      default:
         return <Text>Unsupported media type: {String(mediaItem.type)}</Text>;
   }
}, (p, n) => p.mediaItem === n.mediaItem);

