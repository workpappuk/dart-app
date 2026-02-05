
import React, { useEffect, useState } from 'react';
import { Button, Searchbar, Surface, Avatar, Chip, Divider, Title, Caption, Paragraph, useTheme, IconButton, Card } from 'react-native-paper';
import { AppHeader } from '../components/core/AppHeader';
import { DUMMY_POSTS } from '../utils/constants';
import { Post } from '../utils/types';
import { ScrollView, View, Text, Linking, Animated } from 'react-native';
import { Image } from 'expo-image';
import { useVideoPlayer, VideoView } from 'expo-video';
import { useEvent } from 'expo';
import type { MediaItem } from '../utils/types';

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
                              {(mediaItem as any).caption && <Caption style={{ marginTop: 4 }}>{(mediaItem as any).caption}</Caption>}
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


function VoteControls({ postId, votes, userVote, onVote, vertical }: { postId: string; votes: number; userVote: number; onVote: (postId: string, vote: -1 | 0 | 1) => void; vertical?: boolean }) {
   const theme = useTheme();
   const scale = React.useRef(new Animated.Value(1)).current;
   const prev = React.useRef(votes);

   React.useEffect(() => {
      if (prev.current !== votes) {
         Animated.sequence([
            Animated.timing(scale, { toValue: 1.15, duration: 120, useNativeDriver: true }),
            Animated.timing(scale, { toValue: 1, duration: 120, useNativeDriver: true })
         ]).start();
         prev.current = votes;
      }
   }, [votes, scale]);

   const isVertical = !!vertical;
   if (isVertical) {
      return (
         <View style={{ width: 56, alignItems: 'center' }}>
            <IconButton
               icon="thumb-up"
               size={28}
               onPress={() => onVote(postId, userVote === 1 ? 0 : 1)}
               iconColor={userVote === 1 ? '#fff' : theme.colors.onBackground}
               containerColor={userVote === 1 ? theme.colors.primary : 'transparent'}
               accessibilityLabel="Upvote"
            />
            <Animated.Text style={{ transform: [{ scale }], fontWeight: 'bold', color: theme.colors.onBackground }}>{votes}</Animated.Text>
            <IconButton
               icon="thumb-down"
               size={28}
               onPress={() => onVote(postId, userVote === -1 ? 0 : -1)}
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
            onPress={() => onVote(postId, userVote === 1 ? 0 : 1)}
            iconColor={userVote === 1 ? '#fff' : theme.colors.onBackground}
            containerColor={userVote === 1 ? theme.colors.primary : 'transparent'}
            accessibilityLabel="Upvote"
         />
         <Animated.Text style={{ transform: [{ scale }], marginHorizontal: 6, fontWeight: 'bold', color: theme.colors.onBackground }}>{votes}</Animated.Text>
         <IconButton
            icon="thumb-down"
            size={26}
            onPress={() => onVote(postId, userVote === -1 ? 0 : -1)}
            iconColor={userVote === -1 ? '#fff' : theme.colors.onBackground}
            containerColor={userVote === -1 ? theme.colors.primary : 'transparent'}
            accessibilityLabel="Downvote"
         />
      </View>
   );
}

function MediaVideo({ url }: { url: string | number }) {
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
}

function MediaImage({ url }: { url: string }) {
   return <Image source={{ uri: url }} style={{ width: '100%', height: 200 }} />;
}

function MediaText({ text }: { text: string }) {
   return <Text style={{ marginTop: 4 }}>{text}</Text>;
}

function MediaPoll({ question, options }: { question: string; options: string[] }) {
   const theme = useTheme();
   return (
      <View style={{ marginTop: 4 }}>
         <Text>{question}</Text>
         {options.map((option, index) => (
            <Button key={index} mode="outlined" style={{ marginTop: 4 }} color={theme.colors.primary}>
               {option}
            </Button>
         ))}
      </View>
   );
}

function MediaQuote({ quote, author }: { quote: string; author: string }) {
   return (
      <View style={{ marginTop: 4, padding: 12, backgroundColor: '#f0f0f0', borderRadius: 8 }}>
         <Text style={{ fontStyle: 'italic' }}>"{quote}"</Text>
         {author && <Text style={{ marginTop: 4, textAlign: 'right', fontWeight: 'bold' }}>- {author}</Text>}
      </View>
   );

}

function MediaCode({ code, language }: { code: string; language?: string }) {
   return (
      <View style={{ marginTop: 4, padding: 12, backgroundColor: '#e0e0e0', borderRadius: 8 }}>
         {language && <Text style={{ fontWeight: 'bold', marginBottom: 4 }}>{language}</Text>}
         <Text style={{ fontFamily: 'monospace' }}>{code}</Text>
      </View>
   );
}

function MediaFile({ url, caption }: { url?: string; caption?: string }) {
   const theme = useTheme();
   if (!url) return null;
   return (
      <View style={{ marginTop: 4 }}>
         {caption && <Text>{caption}</Text>}
         <Button mode="outlined" onPress={() => Linking.openURL(url)} style={{ marginTop: 8 }} color={theme.colors.primary}>Open file</Button>
      </View>
   );
}

function MediaAudio({ url, caption }: { url?: string; caption?: string }) {
   const theme = useTheme();
   if (!url) return null;
   return (
      <View style={{ marginTop: 4 }}>
         {caption && <Text>{caption}</Text>}
         <Button mode="outlined" onPress={() => Linking.openURL(url)} style={{ marginTop: 8 }} color={theme.colors.primary}>Play audio</Button>
      </View>
   );
}

function MediaEvent({ title, date, description }: { title?: string; date?: string; description?: string }) {
   return (
      <View style={{ marginTop: 4 }}>
         {title && <Text style={{ fontWeight: 'bold' }}>{title}</Text>}
         {date && <Text>{date}</Text>}
         {description && <Text style={{ marginTop: 4 }}>{description}</Text>}
      </View>
   );
}

function MediaLocation({ latitude, longitude, caption }: { latitude?: number; longitude?: number; caption?: string }) {
   const theme = useTheme();
   if (latitude == null || longitude == null) return null;
   const url = `https://www.google.com/maps/search/?api=1&query=${latitude},${longitude}`;
   return (
      <View style={{ marginTop: 4 }}>
         {caption && <Text>{caption}</Text>}
         <Button mode="outlined" onPress={() => Linking.openURL(url)} style={{ marginTop: 8 }} color={theme.colors.primary}>Open map</Button>
      </View>
   );
}

function MediaProduct({ name, price, url, caption }: { name?: string; price?: number; url?: string; caption?: string }) {
   const theme = useTheme();
   return (
      <View style={{ marginTop: 4 }}>
         {name && <Text style={{ fontWeight: 'bold' }}>{name}</Text>}
         {typeof price === 'number' && <Text>{`$${price}`}</Text>}
         {caption && <Text style={{ marginTop: 4 }}>{caption}</Text>}
         {url && <Button mode="outlined" onPress={() => Linking.openURL(url)} style={{ marginTop: 8 }} color={theme.colors.primary}>View product</Button>}
      </View>
   );
}

function MediaProfile({ username, displayName, avatarUrl, caption }: { username?: string; displayName?: string; avatarUrl?: string; caption?: string }) {
   return (
      <View style={{ marginTop: 4 }}>
         <Text style={{ fontWeight: 'bold' }}>{displayName || username}</Text>
         {caption && <Text>{caption}</Text>}
      </View>
   );
}

function MediaAnnouncement({ title, content }: { title?: string; content?: string }) {
   return (
      <View style={{ marginTop: 4, padding: 8, backgroundColor: '#fff3cd', borderRadius: 6 }}>
         {title && <Text style={{ fontWeight: 'bold' }}>{title}</Text>}
         {content && <Text style={{ marginTop: 4 }}>{content}</Text>}
      </View>
   );
}

function MediaBadge({ name, description, imageUrl }: { name?: string; description?: string; imageUrl?: string }) {
   return (
      <View style={{ marginTop: 4 }}>
         {name && <Text style={{ fontWeight: 'bold' }}>{name}</Text>}
         {description && <Text>{description}</Text>}
      </View>
   );
}

function MediaSimpleList({ title, items }: { title?: string; items?: string[] }) {
   return (
      <View style={{ marginTop: 4 }}>
         {title && <Text style={{ fontWeight: 'bold', marginBottom: 4 }}>{title}</Text>}
         {items?.map((it, i) => <Text key={i}>• {it}</Text>)}
      </View>
   );
}

function MediaInfographic({ url, caption }: { url?: string; caption?: string }) {
   if (!url) return null;
   return (
      <View style={{ marginTop: 4 }}>
         {caption && <Text>{caption}</Text>}
         <Image source={{ uri: url }} style={{ width: '100%', height: 200, marginTop: 8 }} />
      </View>
   );
}

function MediaTable({ headers, rows }: { headers?: string[]; rows?: (string | number)[][] }) {
   return (
      <View style={{ marginTop: 4 }}>
         {headers && <Text style={{ fontWeight: 'bold' }}>{headers.join(' | ')}</Text>}
         {rows?.map((r, i) => <Text key={i}>{r.join(' | ')}</Text>)}
      </View>
   );
}

function MediaSlideshow({ slides }: { slides?: { url?: string; caption?: string }[] }) {
   return (
      <View style={{ marginTop: 4 }}>
         {slides?.map((s, i) => (
            <View key={i} style={{ marginBottom: 8 }}>
               {s.caption && <Text>{s.caption}</Text>}
               {s.url && <Image source={{ uri: s.url }} style={{ width: '100%', height: 180, marginTop: 6 }} />}
            </View>
         ))}
      </View>
   );
}

function MediaGallery({ images }: { images?: { url?: string; caption?: string }[] }) {
   return (
      <View style={{ marginTop: 4 }}>
         {images?.map((img, i) => (
            <Image key={i} source={{ uri: img.url }} style={{ width: '100%', height: 150, marginBottom: 8 }} />
         ))}
      </View>
   );
}

function MediaFAQ({ faqs }: { faqs?: { question: string; answer: string }[] }) {
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
}

function MediaLink({ url, caption }: { url?: string; caption?: string }) {
   const theme = useTheme();
   if (!url) return null;
   return (
      <View style={{ marginTop: 4 }}>
         {caption && <Text>{caption}</Text>}
         <Button mode="outlined" onPress={() => Linking.openURL(url)} style={{ marginTop: 8 }} color={theme.colors.primary}>Open link</Button>
      </View>
   );
}

function MediaItemRenderer({ mediaItem }: { mediaItem: MediaItem }) {
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
         return <MediaFile url={(mediaItem as any).url} caption={(mediaItem as any).caption} />;
      case 'audio':
         return <MediaAudio url={(mediaItem as any).url} caption={(mediaItem as any).caption} />;
      case 'event':
         return <MediaEvent title={(mediaItem as any).title} date={(mediaItem as any).date} description={(mediaItem as any).description} />;
      case 'location':
      case 'map':
         return <MediaLocation latitude={(mediaItem as any).latitude} longitude={(mediaItem as any).longitude} caption={(mediaItem as any).caption} />;
      case 'product':
         return <MediaProduct name={(mediaItem as any).name} price={(mediaItem as any).price} url={(mediaItem as any).url} caption={(mediaItem as any).caption} />;
      case 'profile':
         return <MediaProfile username={(mediaItem as any).username} displayName={(mediaItem as any).displayName} avatarUrl={(mediaItem as any).avatarUrl} caption={(mediaItem as any).caption} />;
      case 'announcement':
         return <MediaAnnouncement title={(mediaItem as any).title} content={(mediaItem as any).content} />;
      case 'badge':
      case 'achievement':
      case 'milestone':
         return <MediaBadge name={(mediaItem as any).name} description={(mediaItem as any).description} imageUrl={(mediaItem as any).imageUrl} />;
      case 'testimonial':
         return <MediaQuote quote={(mediaItem as any).content || (mediaItem as any).text || ''} author={(mediaItem as any).author || ''} />;
      case 'caseStudy':
         return <MediaSimpleList title={(mediaItem as any).title} items={[(mediaItem as any).content || '']} />;
      case 'infographic':
         return <MediaInfographic url={(mediaItem as any).url} caption={(mediaItem as any).caption} />;
      case 'timeline':
         return <MediaSimpleList title={(mediaItem as any).caption} items={(mediaItem as any).events?.map((e: any) => `${e.date}: ${e.event}`) || []} />;
      case 'chart':
         return <MediaSimpleList title={(mediaItem as any).caption} items={[(mediaItem as any).chartType || '', JSON.stringify((mediaItem as any).data || {})]} />;
      case 'table':
         return <MediaTable headers={(mediaItem as any).headers} rows={(mediaItem as any).rows} />;
      case 'slideshow':
         return <MediaSlideshow slides={(mediaItem as any).slides} />;
      case 'animation':
      case 'virtualTour':
      case '3dModel':
         return <MediaLink url={(mediaItem as any).url} caption={(mediaItem as any).caption} />;
      case 'webinar':
      case 'workshop':
         return <MediaEvent title={(mediaItem as any).title} date={(mediaItem as any).date} description={(mediaItem as any).description} />;
      case 'ebook':
      case 'researchPaper':
         return <MediaLink url={(mediaItem as any).url} caption={(mediaItem as any).title || (mediaItem as any).caption} />;
      case 'newsletter':
         return <MediaLink url={(mediaItem as any).url} caption={(mediaItem as any).title} />;
      case 'faq':
         return <MediaFAQ faqs={(mediaItem as any).faqs} />;
      case 'gallery':
         return <MediaGallery images={(mediaItem as any).images} />;
      case 'link':
         return <MediaLink url={(mediaItem as any).url} caption={(mediaItem as any).caption} />;
      case 'summary':
         return <MediaText text={(mediaItem as any).text || ''} />;
      case 'reflection':
         return <MediaSimpleList title={(mediaItem as any).prompt} items={[(mediaItem as any).response || '']} />;
      case 'challenge':
      case 'collaboration':
      case 'sponsorship':
         return <MediaSimpleList title={(mediaItem as any).title} items={[(mediaItem as any).description || (mediaItem as any).roles || ''] } />;
      default:
         return <Text>Unsupported media type: {String(mediaItem.type)}</Text>;
   }
}

